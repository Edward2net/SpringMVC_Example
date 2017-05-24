package com.spring.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.spring.web.entity.CustomerVo;
import com.spring.web.entity.UploadedFile;

@Controller
public class IndexController {

	public IndexController() {
		// TODO Auto-generated constructor stub
	}

	private static final Logger logger = LoggerFactory
			.getLogger(IndexController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Locale locale, Model model) {
		logger.info("function -> index");

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
			Locale locale,
			@RequestParam(value = "loginName", required = false) String loginName) {
		logger.info("function -> login");

		ModelAndView mav = new ModelAndView();
		mav.setViewName("mainPage");

		mav.addObject("userName", loginName);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		mav.addObject("nowTime", formattedDate);
		mav.addObject("nowLocal", locale.getCountry().toString());

		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/queryAjax")
	public CustomerVo getSearchResultViaAjax(@RequestBody CustomerVo search) {
		logger.info("function -> queryAjax");
		CustomerVo result = new CustomerVo();

		result.setName(search.getName());
		// logic
		return result;

	}

	@RequestMapping(value = "/queryAjaxX")
	@ResponseBody
	public CustomerVo getSearchResultViaAjaxX(Locale locale, String name) {
		logger.info("function -> queryAjaxX");
		CustomerVo result = new CustomerVo();

		result.setName(name);
		// logic
		return result;

	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String upload(MultipartHttpServletRequest request,
			HttpServletResponse response) {
		
		/*		HttpServletRequest
				https://docs.oracle.com/cd/E17802_01/products/products/servlet/2.1/api/javax.servlet.http.HttpServletRequest.html
		
				Example:
				WebSite: http://localhost:7001/Spring
				This Action Mapping: /Upload
				This Trigger Method: POST
		
				request.getRequestURL().toString()	 
				- http://localhost:7001/Spring/upload
				request.getRequestURI()	 
				- /Spring/upload
				request.getContextPath()
				- /Spring
				request.getMethod() 
				- POST
				request.getServletPath() 
				- /upload
		*/
		

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		MultipartFile file = multipartRequest.getFile("file");
		String filename = file.getOriginalFilename();

		logger.info("Upload File: filename = " + filename);

		Iterator<String> itr = request.getFileNames();

		/*
		 * MultipartFile mpf = request.getFile(itr.next());
		 * Multipart File try { ufile.length = (int)mpf.getSize(); ufile.bytes =
		 * mpf.getBytes(); ufile.type = mpf.getContentType(); ufile.name =
		 * mpf.getOriginalFilename();
		 */

		if (!file.isEmpty()) {
			try {

				byte[] bytes = file.getBytes();

				// System.getProperty
				// http://docs.oracle.com/javase/6/docs/api/java/lang/System.html#getProperties%28%29
				// user.dir 用戶的當前工作目錄 
				String rootPath = System.getProperty("user.dir");

				// File.separator => OS folder separator, Example: windows => \
				File dir = new File(rootPath + File.separator + "tmpFiles");

				if (!dir.exists())
					dir.mkdirs();

				Date today = Calendar.getInstance().getTime();
				DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

				filename = df.format(today) + "-" + filename;

				String updateFileFullName = new String();
				
				// Full name of upload file.
				updateFileFullName = dir.getAbsolutePath() + File.separator
						+ filename;
				File serverFile = new File(updateFileFullName);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server file save location= "
						+ serverFile.getAbsolutePath());

				String downAction = new String();
				downAction = request.getRequestURL().toString().replace(request.getServletPath() , "/download/");
				
				StringBuilder sbResult = new StringBuilder();

				// Display upload image.
				sbResult.append("<img src='" + downAction + filename + "' />");

				sbResult.append("<button id=btnDownloadFile type='button'>Download File</button>");

				return sbResult.toString();
			} catch (Exception e) {
				return "You failed to upload " + filename + " => "
						+ e.getMessage();
			}
		} else {
			return "You failed to upload " + filename
					+ " because the file was empty.";
		}
	}
	
	@RequestMapping(value = "/download/{value}.{sname}")
    @ResponseStatus(value=HttpStatus.OK)
    @ResponseBody
	public void getDownloadFile(HttpServletResponse response,
			@PathVariable String value, @PathVariable String sname) {
		
		logger.info("File: " + value + ", File Type: " + sname);

		try {
			String resultFileName = System.getProperty("user.dir")
					+ File.separator + "tmpFiles" + File.separator + value + "." + sname;

			File file = new File(resultFileName);
			FileInputStream inputStream = new FileInputStream(file);

			response.setContentType("application/x-download");
			response.setContentLength((int) file.length());
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ resultFileName + "\"");
			
			FileCopyUtils.copy(inputStream, response.getOutputStream());

			response.flushBuffer();
		} catch (IOException ex) {
			logger.info("Error writing file to output stream.");
			throw new RuntimeException("IOError writing file to output stream");
		}
	}
	

	@RequestMapping(value = "/downloadResource")
    @ResponseStatus(value=HttpStatus.OK)
    @ResponseBody
	public void getDownloadResource(HttpServletRequest request, HttpServletResponse response) {
		try {
	        String fileName = "SampleSpringFile.txt";
	        
	        logger.info("Download File:" + fileName );
	        
	        // Convert String to bytes 
	        String sampleContent ="Spring MVC : File upload and download example";
	        byte[] bytes = sampleContent.getBytes();
	         
	        
			response.setContentType("application/x-download");
			response.setContentLength((int) bytes.length);
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ fileName + "\"");
	         
	        OutputStream out = response.getOutputStream();
	        out.write(bytes);
	        out.flush();
	        out.close();

		} catch (IOException ex) {
			logger.info("Error writing file to output stream.");
			throw new RuntimeException("IOError writing file to output stream");
		}
	}
	
}
