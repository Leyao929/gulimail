package com.atguigu.gulimall.search.controller;

import com.atguigu.gulimall.commons.bean.Resp;
import com.atguigu.gulimall.commons.to.es.EsSKuVo;

import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class EsSaveController {


    @Autowired
    private JestClient jestClient;

    @ApiOperation("将数据保存到es中")
    @PostMapping("/es/save")
    public Resp<Object> saveInfoToEs(@RequestBody List<EsSKuVo> esSKuVos){

        esSKuVos.forEach(esSKuVo -> {

            Index builder = new Index.Builder(esSKuVo).index("gulimall").type("spu").id(esSKuVo.getId().toString()).build();

            try {
                jestClient.execute(builder);

            } catch (IOException e) {
                e.printStackTrace();

            }

        });


      return Resp.ok(null);


    }

}
