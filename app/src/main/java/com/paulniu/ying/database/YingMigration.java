package com.paulniu.ying.database;

import com.niupuyue.mylibrary.utils.BaseUtility;
import com.paulniu.ying.constant.AppConfig;

import java.util.Iterator;
import java.util.Map;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Coder: niupuyue
 * Date: 2019/7/15
 * Time: 18:32
 * Desc: 更新数据库操作
 * Version:
 */
public class YingMigration implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        // 获取当前数据库Schema对象
        RealmSchema schema = realm.getSchema();
        // TODO 暂时用不上
//        if (oldVersion == AppConfig.REALM_SQLITE_VERSION) {
//            schema.create("Person")
//                    .addField("name", String.class)
//                    .addField("age", int.class);
//            schema.get("Person")
//                    .addField("id", long.class, FieldAttribute.PRIMARY_KEY)
//                    .addRealmObjectField("favoriteDog", schema.get("Dog"))
//                    .removeField("name")
//                    .renameField("id", "userId")
//                    .addRealmListField("dogs", schema.get("Dog"));
//            oldVersion++;
//        }
    }

    /**
     * 写完之后，发现没啥用
     *
     * @param schema
     * @param table
     * @param fields
     */
    private void create(RealmSchema schema, String table, Map<String, Class> fields) {
        if (null == schema || BaseUtility.isEmpty(fields)) return;
        try {
            RealmObjectSchema realmObjectSchema = schema.create(table);
            Iterator keyIt = fields.keySet().iterator();
            while (keyIt.hasNext()) {
                String key = (String) keyIt.next();
                Class clz = fields.get(key);
                realmObjectSchema.addField(key, clz);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
