/**
 * 
 */
package com.azhen.code;

import com.azhen.core.validate.core.ValidateCodeGenerator;
import com.azhen.core.validate.core.image.ImageCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author zhailiang
 *
 */
//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {

	/* (non-Javadoc)
	 * @see com.imooc.security.core.validate.code.ValidateCodeGenerator#generate(org.springframework.web.context.request.ServletWebRequest)
	 */
	@Override
	public ImageCode generate(ServletWebRequest request) {
		System.out.println("更高级的图形验证码生成代码");
		return null;
	}

}
