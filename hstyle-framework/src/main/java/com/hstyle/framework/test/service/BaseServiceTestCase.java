package com.hstyle.framework.test.service;

import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.hstyle.framework.utils.ConvertUtil;
@ContextConfiguration(
        locations = {"classpath:/applicationContext-test.xml"})
public class BaseServiceTestCase extends
		AbstractTransactionalJUnit4SpringContextTests {
	   /**
     * A simple logger
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());
    /**
     * The resourceBundle
     */
    protected ResourceBundle rb;

    /**
     * Default constructor will set the ResourceBundle if needed.
     */
    public BaseServiceTestCase() {
        // Since a ResourceBundle is not required for each class, just
        // do a simple check to see if one exists
        String className = this.getClass().getName();

        try {
            rb = ResourceBundle.getBundle(className);
        } catch (MissingResourceException mre) {
            // log.warn("No resource bundle found for: " + className);
        }
    }

    /**
     * Utility method to populate an object with values from a properties file
     *
     * @param obj the model object to populate
     * @return Object populated object
     * @throws Exception if BeanUtils fails to copy properly
     */
    protected Object populate(Object obj) throws Exception {
        // loop through all the beans methods and set its properties from
        // its .properties file
        Map map = ConvertUtil.convertBundleToMap(rb);

        BeanUtils.copyProperties(obj, map);

        return obj;
    }
}
