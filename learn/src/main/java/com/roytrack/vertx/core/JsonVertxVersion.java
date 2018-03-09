package com.roytrack.vertx.core;

import com.roytrack.vertx.core.json.TmpVo;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.Map;

/***
 * vertx json implement with jackson
 *   Created by roytrack on 2018-03-09  16:19
 */
public class JsonVertxVersion {
    public static void main(String[] args) {
        String jsonStr="{\"foo\":\"bar\"}";
        JsonObject object=new JsonObject(jsonStr);
        System.out.println(object.encodePrettily());

        Map<String,Object> map=new HashMap();
        map.put("foo","bar");
        map.put("xyz",3);
        JsonObject object1=new JsonObject(map);
        System.out.println(object1.encodePrettily());

        JsonObject object2=new JsonObject();
        object2.put("foo",666).put("str","roytrack").put("bool",true);
        System.out.println(object2.encodePrettily());

        String val=object.getString("foo");
        System.out.println(val);

        TmpVo tmpVo=object2.mapTo(TmpVo.class);
        System.out.println(tmpVo);


    }

}
