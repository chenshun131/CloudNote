package com.chenshun.test.concurrency.forkandjoin.pool;

import java.util.ArrayList;
import java.util.List;

/**
 * User: mew <p />
 * Time: 17/9/11 14:11  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class ProductListGenerator {

    public List<Product> generate(int size) {
        List<Product> ret = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Product product = new Product();
            product.setName("Product " + i);
            product.setPrice(10);
            ret.add(product);
        }
        return ret;
    }

}
