package com.pan.spring.io.loader;

import cn.hutool.core.lang.Assert;
import com.pan.spring.io.Resource;
import com.pan.spring.io.ResourceLoader;
import com.pan.spring.io.resource.ClassPathResource;
import com.pan.spring.io.resource.FileSystemResource;
import com.pan.spring.io.resource.UrlResource;

import java.net.MalformedURLException;
import java.net.URL;

public class DefaultResourceLoader implements ResourceLoader {

    @Override
    public Resource getResource(String location) {
        Assert.notNull(location, "[Error] location can not be null.");
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        } else {
            try {
                URL url = new URL(location);
                return new UrlResource(url);
            } catch (MalformedURLException e) {
                return new FileSystemResource(location);
            }
        }
    }
}
