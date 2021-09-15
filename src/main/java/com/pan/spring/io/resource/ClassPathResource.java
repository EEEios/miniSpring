package com.pan.spring.io.resource;

import com.pan.spring.util.ClassUtils;
import com.pan.spring.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ClassPathResource implements Resource {

    private final String path;

    private ClassLoader classLoader;

    public ClassPathResource(String path){
        this(path, (ClassLoader)null);
    }

    public ClassPathResource(String path, ClassLoader classLoader){
        this.path = path;
        this.classLoader = classLoader!=null? classLoader : ClassUtils.getDefaultClassLoader();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream inputStream = classLoader.getResourceAsStream(path);
        if (inputStream == null){
            throw new FileNotFoundException("[Error]" + this.path + " not exist.");
        }
        return inputStream;
    }
}
