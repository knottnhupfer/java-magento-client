package com.github.chen0040.magento;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chen0040.magento.models.Product;
import com.github.chen0040.magento.models.ProductPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;


/**
 * Created by xschen on 12/6/2017.
 */
public class MagentoClientUnitTest {

   private static final Logger logger = LoggerFactory.getLogger(MagentoClientUnitTest.class);
   @Test
   public void test_login_client(){
      MagentoClient client = new MagentoClient(Mediator.url);
      String token = client.loginAsClient(Mediator.customerUsername, Mediator.customerPassword);
      logger.info("my account:\r\n{}", JSON.toJSONString(client.getMyAccount(), SerializerFeature.PrettyFormat));
      logger.info("product types:\r\n{}", JSON.toJSONString(client.listProductTypes(), SerializerFeature.PrettyFormat));
   }

   @Test
   public void test_list_product(){
      MagentoClient client = new MagentoClient(Mediator.url);
      String token = client.loginAsAdmin(Mediator.adminUsername, Mediator.adminPassword);
      logger.info("account with id = 1: {}", client.getAccountById(1));
      logger.info("product types: \r\n{}", JSON.toJSONString(client.listProductTypes(), SerializerFeature.PrettyFormat));

      ProductPage page  = client.listProducts(0, 10);
      logger.info("product page: \r\n{}", JSON.toJSONString(page, SerializerFeature.PrettyFormat));
      Product p1 = page.getItems().get(0);
      Product p2 = client.getProductBySku(p1.getSku());
      logger.info("product:\r\n{}", JSON.toJSONString(p2, SerializerFeature.PrettyFormat));
   }

   @Test
   public void test_get_product(){
      MagentoClient client = new MagentoClient(Mediator.url);
      client.loginAsAdmin(Mediator.adminUsername, Mediator.adminPassword);

      Product p1 = client.getProductBySku("B201-SKU");
      logger.info("product:\r\n{}", JSON.toJSONString(p1, SerializerFeature.PrettyFormat));
      Product p2 = client.getProductBySku("B202-SKU");
      logger.info("product:\r\n{}", JSON.toJSONString(p2, SerializerFeature.PrettyFormat));
   }

   @Test
   public void test_delete_product(){
      MagentoClient client = new MagentoClient(Mediator.url);
      client.loginAsAdmin(Mediator.adminUsername, Mediator.adminPassword);

      String sku = "B203-SKU";
      logger.info("product exists ? {}", client.hasProduct(sku));
      logger.info("client.deleteProduct(sku): {}", client.deleteProduct(sku));
      logger.info("product exists ? {}", client.hasProduct(sku));
   }

   @Test
   public void test_add_product() {
      MagentoClient client = new MagentoClient(Mediator.url);
      client.loginAsAdmin(Mediator.adminUsername, Mediator.adminPassword);

      Product newProduct = new Product();
      newProduct.setSku("B203-SKU");
      newProduct.setName("B203");
      newProduct.setPrice(30.00);
      newProduct.setStatus(1);
      newProduct.setType_id("simple");
      newProduct.setAttribute_set_id(4);
      newProduct.setWeight(1);

      logger.info("add product result: {}", JSON.toJSONString(client.addProduct(newProduct), SerializerFeature.PrettyFormat));
   }
}