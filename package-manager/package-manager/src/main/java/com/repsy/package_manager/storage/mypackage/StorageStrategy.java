package com.repsy.package_manager.storage.mypackage;

import java.io.InputStream;

public interface StorageStrategy {
    void save(String path, InputStream content);
    InputStream read(String path);
}