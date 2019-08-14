package com.atguigu.gulimall.pms.service.impl;

import com.atguigu.gulimall.commons.bean.Resp;
import com.atguigu.gulimall.commons.to.SkuSaleInfoTo;
import com.atguigu.gulimall.commons.to.es.EsSKuVo;
import com.atguigu.gulimall.commons.to.es.EsSkuAttributeValue;
import com.atguigu.gulimall.commons.to.es.SkuStockVo;
import com.atguigu.gulimall.pms.dao.*;
import com.atguigu.gulimall.pms.entity.*;
import com.atguigu.gulimall.pms.feign.SearchEsSaveFeignService;
import com.atguigu.gulimall.pms.feign.SmsFeignService;
import com.atguigu.gulimall.pms.feign.WmsFeignService;
import com.atguigu.gulimall.pms.util.StringAppendUtils;
import com.atguigu.gulimall.pms.vo.*;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gulimall.commons.bean.PageVo;
import com.atguigu.gulimall.commons.bean.Query;
import com.atguigu.gulimall.commons.bean.QueryCondition;

import com.atguigu.gulimall.pms.service.SpuInfoService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SearchEsSaveFeignService searchEsSaveFeignService;

    @Autowired
    private SpuInfoDescDao spuInfoDescDao;

    @Autowired
    private ProductAttrValueDao productAttrValueDao;

    @Autowired
    private SkuInfoDao skuInfoDao;

    @Autowired
    private WmsFeignService wmsFeignService;

    @Autowired
    private SkuImagesDao skuImagesDao;

    @Autowired
    private SpuInfoDao spuInfoDao;

    @Autowired
    private AttrDao attrDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private SkuSaleAttrValueDao skuSaleAttrValueDao;

    @Autowired
    private SmsFeignService smsFeignService;

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageVo(page);
    }

    @Override
    public PageVo searchSpuInfo(QueryCondition queryCOndition, String key, Long catId) {

        QueryWrapper<SpuInfoEntity> queryWrapper = new QueryWrapper<>();

        if(catId != 0){
            queryWrapper.eq("catalog_id",catId);

            if(!StringUtils.isEmpty(key)){

                queryWrapper.and(obj ->{

                    obj.like("spu_name",key);
                    obj.or().like("id",key);
                    return obj;
                });

            }
        }



        IPage<SpuInfoEntity> page = new Query<SpuInfoEntity>().getPage(queryCOndition);

        IPage<SpuInfoEntity> data = this.page(page, queryWrapper);

        return new PageVo(data);


    }

    @GlobalTransactional(rollbackFor = {Exception.class})
    @Override
    public void insert(ProductSaveVo productSaveVo) {


        List<SkuSaleInfoTo> infoTo = new ArrayList<>();

        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();

        BeanUtils.copyProperties(productSaveVo,spuInfoEntity);

        spuInfoEntity.setCreateTime(new Date());

        spuInfoEntity.setUodateTime(new Date());

        //往spuinfo中添加数据
        spuInfoDao.insert(spuInfoEntity);

        //图片往spuinfo info desc中插入
        String[] spuImages = productSaveVo.getSpuImages();

        String imageDesc = StringAppendUtils.stringAppend(spuImages);

        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();

        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        spuInfoDescEntity.setDecript(imageDesc);

        //插入数据库
        spuInfoDescDao.insertInfo(spuInfoDescEntity);

        //插入product_attr_value表
        BaseAttrVo[] baseAttrs = productSaveVo.getBaseAttrs();
        
        //准备好插入表中的集合数据
        List<ProductAttrValueEntity> attrList = new ArrayList<>();
        
        if(baseAttrs != null && baseAttrs.length > 0){
            for (BaseAttrVo baseAttr : baseAttrs) {

                ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();


                String[] valueSelected = baseAttr.getValueSelected();

                String valueSelect = StringAppendUtils.stringAppend(valueSelected);

                productAttrValueEntity.setAttrValue(valueSelect);

                productAttrValueEntity.setSpuId(spuInfoEntity.getId());

                productAttrValueEntity.setAttrSort(0);

                productAttrValueEntity.setQuickShow(1);

                productAttrValueEntity.setAttrName(baseAttr.getAttrName());

                productAttrValueEntity.setAttrId(baseAttr.getAttrId());

                attrList.add(productAttrValueEntity);
            }


            for (ProductAttrValueEntity productAttrValueEntity : attrList) {

                productAttrValueDao.insert(productAttrValueEntity);

            }

        }


        //插入sku信息
        SkuVo[] skus = productSaveVo.getSkus();



        if(skus != null && skus.length >0){

            for (SkuVo skuVo : skus) {

                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();

                //组装SKuinfoEntity
                skuInfoEntity.setSpuId(spuInfoEntity.getId());

                skuInfoEntity.setSkuCode(UUID.randomUUID().toString().substring(0, 5).toUpperCase());

                skuInfoEntity.setSkuName(skuVo.getSkuName());

                skuInfoEntity.setSkuDesc(skuVo.getSkuDesc());

                skuInfoEntity.setPrice(skuVo.getPrice());

                skuInfoEntity.setCatalogId(productSaveVo.getCatalogId());

                skuInfoEntity.setBrandId(productSaveVo.getBrandId());

                String[] images = skuVo.getImages();

                if(images != null && images.length > 0){

                    skuInfoEntity.setSkuDefaultImg(images[0]);
                }


                skuInfoEntity.setSkuTitle(skuVo.getSkuTitle());

                skuInfoEntity.setSkuSubtitle(skuVo.getSkuSubtitle());

                skuInfoEntity.setWeight(skuVo.getWeight());

                skuInfoDao.insert(skuInfoEntity);


                //往图片表中插入图片
                if(images != null && images.length > 0){

                    for (int i = 0; i < images.length; i++) {

                        SkuImagesEntity skuImagesEntity = new SkuImagesEntity();

                        skuImagesEntity.setImgSort(0);

                        skuImagesEntity.setImgUrl(images[i]);

                        skuImagesEntity.setDefaultImg(i==0?1:0);

                        skuImagesEntity.setSkuId(skuInfoEntity.getSkuId());

                        skuImagesDao.insert(skuImagesEntity);

                    }

                }


                //插入sku_sale_attr_value


                SaleAttrVo[] saleAttrs = skuVo.getSaleAttrs();

                if(saleAttrs !=null && saleAttrs.length >0){

                    for (SaleAttrVo saleAttr : saleAttrs) {

                        SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();

                        skuSaleAttrValueEntity.setAttrId(saleAttr.getAttrId());

                        //通过attrId查询attrName
                        AttrEntity attrEntity = attrDao.selectById(saleAttr.getAttrId());

                        skuSaleAttrValueEntity.setAttrValue(saleAttr.getAttrValue());

                        skuSaleAttrValueEntity.setAttrName(attrEntity.getAttrName());

                        skuSaleAttrValueEntity.setAttrSort(0);

                        skuSaleAttrValueEntity.setSkuId(skuInfoEntity.getSkuId());

                        skuSaleAttrValueDao.insert(skuSaleAttrValueEntity);

                    }

                }


                //封装折扣数据远程传输给sms服务  远程调用sms服务完成数据插入
                SkuSaleInfoTo skuSaleInfoTo = new SkuSaleInfoTo();

                BeanUtils.copyProperties(skuVo,skuSaleInfoTo);

                skuSaleInfoTo.setSkuId(skuInfoEntity.getSkuId());

                infoTo.add(skuSaleInfoTo);

            }


            //发送远程调用
            smsFeignService.saveSaleInfo(infoTo);

            //int i = 1 / 0 ;

        }

        //


    }


    @Override
    public void updateSpuStatu(Integer status, Long spuId) {


        //上架
        if(status == 1){
            //商品上架
            spuUp(status,spuId);
        }else{
            //商品下架
            spuDown(status,spuId);
        }


    }

    //商品上架
    private void spuUp(Integer status, Long spuId) {

        //查出spuinfo
        SpuInfoEntity spuInfo = spuInfoDao.selectById(spuId);

        //查出这个spu下的品牌信息
        BrandEntity brand = brandDao.selectById(spuInfo.getBrandId());

        //查出这个spu的三级标题的信息
        CategoryEntity categoryEntity = categoryDao.selectById(spuInfo.getCatalogId());

        //获取spu下所有的属性
        List<ProductAttrValueEntity> attrValueEntities = productAttrValueDao.selectList(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId));

        List<Long> attrIds = new ArrayList<>();

        //遍历所有的属性选出可以被检索的属性
        if(attrValueEntities != null && attrValueEntities.size() > 0){

            attrValueEntities.forEach(attrValueEntity ->{

                attrIds.add(attrValueEntity.getAttrId());

            });
        }

        //查出可以被检索的属性
        List<AttrEntity> attrEntities = attrDao.selectList(new QueryWrapper<AttrEntity>().in("attr_id", attrIds).eq("search_type", 1));


        List<EsSkuAttributeValue> esSkuAttributeValues = new ArrayList<>();

        attrEntities.forEach(attrEntity -> {

            attrValueEntities.forEach(productAttrValueEntity -> {


                if(productAttrValueEntity.getAttrId() == attrEntity.getAttrId()){

                    EsSkuAttributeValue value = new EsSkuAttributeValue();

                    value.setId(productAttrValueEntity.getId());

                    value.setProductAttributeId(productAttrValueEntity.getAttrId());

                    value.setName(productAttrValueEntity.getAttrName());

                    value.setValue(productAttrValueEntity.getAttrValue());

                    value.setSpuId(productAttrValueEntity.getSpuId());

                    esSkuAttributeValues.add(value);

                }

            });

        });

        //封装EsSkuVo信息让search服务存到es中
        List<EsSKuVo> esSKuVos = new ArrayList<>();

        //1.根据spuid查出所有的sku信息
        List<SkuInfoEntity> skuInfoEntityList = skuInfoDao.selectList(new QueryWrapper<SkuInfoEntity>().eq("spu_id", spuId));

        if(skuInfoEntityList !=null && skuInfoEntityList.size() > 0){

            List<Long> skuIds = new ArrayList<>();

            skuInfoEntityList.forEach(skuInfoEntity -> {
                //获取每个sku的库存集合统一添加到集合中

                skuIds.add(skuInfoEntity.getSkuId());
            });

            //远程调用wms服务获取库存信息
            Resp<List<SkuStockVo>> resp = wmsFeignService.getStock(skuIds);

            List<SkuStockVo> skuStockVos = resp.getData();

            //遍历集合
            skuInfoEntityList.forEach(skuInfoEntity -> {


                EsSKuVo esSKuVo = skuInfoToEsSkuVo(skuInfoEntity,brand,categoryEntity,skuStockVos,esSkuAttributeValues);

                esSKuVos.add(esSKuVo);

            });
        }

        //远程调用search服务
        Resp<Object> resp = searchEsSaveFeignService.saveInfoToEs(esSKuVos);

        if(resp.getCode() == 0){
            //保存到es中成功 更新数据库把发布状态改为1
            SpuInfoEntity spuInfoEntity = new SpuInfoEntity();

            spuInfoEntity.setId(spuId);

            spuInfoEntity.setPublishStatus(1);

            spuInfoEntity.setUodateTime(new Date());

            spuInfoDao.updateById(spuInfoEntity);


        }


    }

    /*
    private Long id;//skuid

    private Long brandId;//品牌id

    private String brandName;//品牌名称

    private Long productCategoryId;//三级标题id

    private String productCategoryName;//三级标题名称




    private String pic;//图片

    private String name;//sku标题

    private BigDecimal price;//价格

    private Integer sale;//销量

    private Integer stock;

    private Integer sort;//库存

    //保存当前sku所需要检索的属性
    private List<EsSkuAttributeValue> attrValueList;
     */

    //将skuInfoEntity中的数据封装为EsSkuVo中
    private EsSKuVo skuInfoToEsSkuVo(SkuInfoEntity skuInfoEntity, BrandEntity brand, CategoryEntity categoryEntity, List<SkuStockVo> skuStockVos, List<EsSkuAttributeValue> esSkuAttributeValues) {

        EsSKuVo esSKuVo = new EsSKuVo();

        //设置skuid
        esSKuVo.setId(skuInfoEntity.getSkuId());

        //设置品牌id
        esSKuVo.setBrandId(skuInfoEntity.getBrandId());

        //设置品牌名称
        //因为同一个spu的品牌都是相同的 所以可以统一在外面先把品牌名字先查出来
        if(brand != null){
            esSKuVo.setBrandName(brand.getName());
        }

        //设置三级标题
        esSKuVo.setProductCategoryId(skuInfoEntity.getCatalogId());

        //设置三级标题的名称
        //因为同一个spu的三级标题都是相同的 所以可以统一在外面先把三级标题名字先查出来
        if(categoryEntity != null){
            esSKuVo.setProductCategoryName(categoryEntity.getName());
        }

        //设置图片
        esSKuVo.setPic(skuInfoEntity.getSkuDefaultImg());

        //设置sku标题
        esSKuVo.setName(skuInfoEntity.getSkuTitle());

        //设置价格
        esSKuVo.setPrice(skuInfoEntity.getPrice());

        //设置销量  老雷设计数据库的时候忘了设置这个字段  所以先都设为0....
        esSKuVo.setSale(0);

        //设置库存
        //需要远程调用wms服务获取库存数量  遍历集合
        skuStockVos.forEach(skuStockVo -> {

            if(skuStockVo.getSkuId() == skuInfoEntity.getSkuId()){

                esSKuVo.setStock(skuStockVo.getStock());
            }

        });

        //设置排序
        esSKuVo.setSort(0);

        //设置sku当前需要检索的属性
        esSKuVo.setAttrValueList(esSkuAttributeValues);

        return esSKuVo;

    }

    //商品下架
    private void spuDown(Integer status, Long spuId) {



    }

}