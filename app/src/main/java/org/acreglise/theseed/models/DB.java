package org.acreglise.theseed.models;

import io.realm.Realm;

public class DB {
    public static void deleteAll() {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
    }

    public static void refresh() {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                for (FrResourceContent content : realm.where(FrResourceContent.class).findAll()) {
                    content.setSelected(false);
                }
            }
        });
    }
}
