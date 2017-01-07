package com.romao.nhlspider.storage.impl.realm;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by rpiontkovsky on 1/3/2017.
 */

public abstract class RealmObjectStorage<E extends RealmObject> {

    protected final Class<E> typeParameterClass;
    protected final RealmConfiguration realmConfiguration;

    public RealmObjectStorage(RealmConfiguration realmConfiguration, Class<E> typeParameterClass) {
        this.realmConfiguration = realmConfiguration;
        this.typeParameterClass = typeParameterClass;
    }

    public synchronized List<E> readAll() {
        Realm realm = Realm.getInstance(realmConfiguration);
        List<E> result = realm.where(typeParameterClass).findAll();
        return realm.copyFromRealm(result);
    }

    protected void performTransaction(StorageTransaction transaction) {
        Realm realm = Realm.getInstance(realmConfiguration);
        realm.beginTransaction();
        transaction.execute(realm);
        realm.commitTransaction();
    }

    protected RealmQuery<E> prepareQuery() {
        Realm realm = Realm.getInstance(realmConfiguration);
        return realm.where(typeParameterClass);
    }

    protected E copy(E e) {
        Realm realm = Realm.getInstance(realmConfiguration);
        return e == null ? null : realm.copyFromRealm(e);
    }

    protected List<E> copy(RealmResults<E> results) {
        Realm realm = Realm.getInstance(realmConfiguration);
        return realm.copyFromRealm(results);
    }

    protected List<E> executeQuery(RealmQuery<E> query) {
        Realm realm = Realm.getInstance(realmConfiguration);
        return realm.copyFromRealm(query.findAll());
    }

    protected E first(List<E> values) {
        if (values == null || values.isEmpty()) {
            return null;
        }

        return values.get(0);
    }


    protected interface StorageTransaction {
        void execute(Realm realm);
    }
}
