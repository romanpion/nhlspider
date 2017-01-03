package com.romao.nhlspider.storage;

import java.util.List;

/**
 * Created by rpiontkovsky on 1/3/2017.
 */

public interface ObjectStorage<E extends Object> {

    List<E> readAll();
}
