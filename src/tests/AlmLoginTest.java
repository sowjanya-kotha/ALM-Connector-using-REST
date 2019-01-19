package tests;

import infrastructure.Constants;
import infrastructure.Entity;
import infrastructure.Entity.Fields.Field;
import infrastructure.EntityMarshallingUtils;
import infrastructure.Response;
import infrastructure.RestConnector;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alm.AlmConnector;

public class AlmLoginTest {

	public void testAlmLogin() throws Exception {
		AlmConnector alm = new AlmConnector();

		RestConnector conn = RestConnector.getInstance();
		conn.init(new HashMap<String, String>(), Constants.HOST + ":" +
				Constants.PORT + "/qcbin",
				Constants.DOMAIN, Constants.PROJECT);

		alm.login(Constants.USERNAME, Constants.PASSWORD);
		System.out.println("isAuthenticated" + alm.isAuthenticated());
		alm.logout();
	}

	public static void main(String[] args) {
		AlmLoginTest test = new AlmLoginTest();
		try {
//			test.testReadDefect();
//			test.testGetTestsInTestLab();
//			test.testGetFoldersInTestSets();
//			test.uploadAttachment();
//			test.getMandatoryFieldsInEntity();
			test.testGetTestRunsInTestLab();
			Date date = new Date();
			System.out.println(date.toString());
			
//			System.out.println(InetAddress.getLocalHost().getHostName());
			/*String fileName = "Test case 1.testcase";
			String fileextension = ".testcase";
			
			System.out.println(fileName.substring(0, fileName.lastIndexOf(fileextension)));*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testReadDefect() throws Exception {
		AlmConnector alm = new AlmConnector();

		try {	
			RestConnector conn = RestConnector.getInstance();
			conn.init(new HashMap<String, String>(), Constants.HOST + ":"
					+ Constants.PORT + "/qcbin", Constants.DOMAIN,
					Constants.PROJECT);
			alm.createSession(Constants.USERNAME, Constants.PASSWORD);
			/**
			 * Get the defect with id 1.
			 */
			String defectUrl = conn.buildEntityCollectionUrl("defect");
			defectUrl += "/1";
			Map<String, String> requestHeaders = new HashMap<String, String>();
			requestHeaders.put("Accept", "application/json");
			Response res = conn.httpGet(defectUrl, null, requestHeaders);

			if(res.getStatusCode() == HttpURLConnection.HTTP_OK || res.getStatusCode() == HttpURLConnection.HTTP_CREATED) {
				// xml -> class instance
				String postedEntityReturnedXml = res.toString();
				System.out.println();
				Entity entity = EntityMarshallingUtils.marshal(Entity.class,
						postedEntityReturnedXml);
				/**
				 * Print all names available in entity defect to screen.
				 */
				List<Field> fields = entity.getFields().getField();
				for (Field field : fields) {
					System.out.println(field.getName() + " : "
							+ field.getValue().size());
				}
			} else {
				throw res.getFailure();
			}
		} finally  {
			alm.logout();
			alm = null;
		}


	}

	public void testCreateTestinTestLab() throws Exception {
		AlmConnector alm = new AlmConnector();

		try {	
			RestConnector conn = RestConnector.getInstance();
			conn.init(new HashMap<String, String>(), Constants.HOST + ":"
					+ Constants.PORT + "/qcbin", Constants.DOMAIN,
					Constants.PROJECT);
			alm.createSession(Constants.USERNAME, Constants.PASSWORD);

			String url = conn.buildEntityCollectionUrl("test");
			Map<String, String> requestHeaders = new HashMap<String, String>();
			requestHeaders.put("Accept", "application/xml");
			requestHeaders.put("Content-Type", "application/xml");

			String postData = "<Entity Type=\"test\"><Fields><Field Name=\"parent-id\"><Value>1006</Value></Field><Field Name=\"name\"><Value>provar testcase1</Value></Field><Field Name=\"subtype-id\"><Value>MANUAL</Value></Field></Fields></Entity>";
			Response res = conn.httpPost(url, postData.getBytes(), requestHeaders);
			if(res.getStatusCode() == HttpURLConnection.HTTP_OK || res.getStatusCode() == HttpURLConnection.HTTP_CREATED) {
				String postedEntityReturnedXml = res.toString();
				System.out.println(postedEntityReturnedXml);
				Entity entity = EntityMarshallingUtils.marshal(Entity.class,
						postedEntityReturnedXml);
				List<Field> fields = entity.getFields().getField();
				for (Field field : fields) {
					System.out.println(field.getName() + " : "
							+ field.getValue().size());
				}
			} else {
				System.out.println(res.toString());
				throw res.getFailure();
			}
		} finally  {
			alm.logout();
			alm = null;
		}
	}

	public void testGetTestsInTestLab() throws Exception {
		AlmConnector alm = new AlmConnector();

		try {	
			RestConnector conn = RestConnector.getInstance();
			conn.init(new HashMap<String, String>(), Constants.HOST + ":"
					+ Constants.PORT + "/qcbin", Constants.DOMAIN,
					Constants.PROJECT);
			alm.createSession(Constants.USERNAME, Constants.PASSWORD);

			String url = conn.buildEntityCollectionUrl("test-set");
			//			url += "/6";

			Map<String, String> requestHeaders = new HashMap<String, String>();
			requestHeaders.put("Accept", "application/json");
			requestHeaders.put("Content-Type", "application/json");

			Response res = conn.httpGet(url, null, requestHeaders);
			if(res.getStatusCode() == HttpURLConnection.HTTP_OK || res.getStatusCode() == HttpURLConnection.HTTP_CREATED) {
				String postedEntityReturnedXml = res.toString();
				System.out.println(postedEntityReturnedXml);
				/*Entities entities = EntityMarshallingUtils.marshal(Entities.class,
						postedEntityReturnedXml);
				List<Entity> list = entities.getEntities();
				for(Entity entity: list) {
					List<Field> fields = entity.getFields().getField();
					for (Field field : fields) {
						System.out.println(field.getName() + " : "
								+ field.getValue().size());
					}
				}	*/			
			} else {
				System.out.println(res.toString());
				throw res.getFailure();
			}
		} finally  {
			alm.logout();
			alm = null;
		}
	}

	public void testGetFoldersInTestSets() throws Exception {
		AlmConnector alm = new AlmConnector();

		try {
			RestConnector conn = RestConnector.getInstance();
			conn.init(new HashMap<String, String>(), Constants.HOST + ":"
					+ Constants.PORT + "/qcbin", Constants.DOMAIN,
					Constants.PROJECT);
			alm.createSession(Constants.USERNAME, Constants.PASSWORD);

			//			String url = conn.buildEntityCollectionUrl("test-set-folder");//Test Lab
			//			String url = conn.buildEntityCollectionUrl("test-folder"); //Test Plan
			//			String url = conn.buildEntityCollectionUrl("resource-folder"); //Test Resources
			String url = conn.buildEntityCollectionUrl("resource");
			url = url+"/1002/storage/template 1.xml";
			Map<String, String> requestHeaders = new HashMap<String, String>();
			requestHeaders.put("Accept", "application/json");

			Response res = conn.httpGet(url, null, requestHeaders);
			if(res.getStatusCode() == HttpURLConnection.HTTP_OK || res.getStatusCode() == HttpURLConnection.HTTP_CREATED) {
				String postedEntityReturnedXml = res.toString();
				System.out.println(postedEntityReturnedXml);
				/*Entities entities = EntityMarshallingUtils.marshal(Entities.class,
						postedEntityReturnedXml);
				List<Entity> list = entities.getEntities();
				for(Entity entity: list) {
					List<Field> fields = entity.getFields().getField();
					for (Field field : fields) {
						System.out.println(field.getName() + " : "
								+ field.getValue().size());
					}
				}*/
			}
		} finally  {
			alm.logout();
			alm = null;
		}
	}

	public void testCreateTestinTestPlan() throws Exception {
		AlmConnector alm = new AlmConnector();

		try {	
			RestConnector conn = RestConnector.getInstance();
			conn.init(new HashMap<String, String>(), Constants.HOST + ":"
					+ Constants.PORT + "/qcbin", Constants.DOMAIN,
					Constants.PROJECT);
			alm.createSession(Constants.USERNAME, Constants.PASSWORD);

			String url = conn.buildEntityCollectionUrl("test");
			Map<String, String> requestHeaders = new HashMap<String, String>();
			requestHeaders.put("Accept", "application/xml");
			requestHeaders.put("Content-Type", "application/xml");

			String postData = "<Entity Type=\"test\"><Fields><Field Name=\"parent-id\"><Value>1006</Value></Field><Field Name=\"name\"><Value>provar testcase1</Value></Field><Field Name=\"subtype-id\"><Value>MANUAL</Value></Field></Fields></Entity>";
			Response res = conn.httpPost(url, postData.getBytes(), requestHeaders);
			if(res.getStatusCode() == HttpURLConnection.HTTP_OK || res.getStatusCode() == HttpURLConnection.HTTP_CREATED) {
				String postedEntityReturnedXml = res.toString();
				System.out.println(postedEntityReturnedXml);
				Entity entity = EntityMarshallingUtils.marshal(Entity.class,
						postedEntityReturnedXml);
				List<Field> fields = entity.getFields().getField();
				for (Field field : fields) {
					System.out.println(field.getName() + " : "
							+ field.getValue().size());
				}
			} else {
				System.out.println(res.toString());
				throw res.getFailure();
			}
		} finally  {
			alm.logout();
			alm = null;
		}
	}


	public void uploadAttachment() throws Exception {
		AlmConnector alm = new AlmConnector();

		try {
			RestConnector conn = RestConnector.getInstance();
			conn.init(new HashMap<String, String>(), Constants.HOST + ":"
					+ Constants.PORT + "/qcbin", Constants.DOMAIN,
					Constants.PROJECT);
			alm.createSession(Constants.USERNAME, Constants.PASSWORD);

//			String url = conn.buildEntityCollectionUrl("test");
//			url += "/26/attachments";
			String url = conn.buildEntityCollectionUrl("resource");
			url += "/1007/storage";
			System.out.println(url);
			Map<String, String> requestHeaders = new HashMap<String, String>();
//			requestHeaders.put("Accept", "application/json");
//			requestHeaders.put("Slug", "template2Rest.xml");
			requestHeaders.put("Content-Type", "application/octet-stream");

			File file = new File("E:/Work/ALM WorkArea/ALM Connector using REST/src/tests/template 1.xml");
			Path path = Paths.get(file.getAbsolutePath());
			byte[] data = Files.readAllBytes(path);
			
			System.out.println(new String(data));
			Response res = conn.httpPost(url, data, requestHeaders);
			if(res.getStatusCode() == HttpURLConnection.HTTP_OK || res.getStatusCode() == HttpURLConnection.HTTP_CREATED) {
				String postedEntityReturnedXml = res.toString();
				System.out.println(postedEntityReturnedXml);
			} else {
				System.out.println(res.toString());
				throw res.getFailure();
			}
		} finally  {
			alm.logout();
			alm = null;
		}
	}
	
	/**
	 * run ==> owner, testcycl-id(which is test instance id), test-id, status, name, cycle-id(which is test set id)
	 * test-instance ==> test-id, cycle-id (which is test-set)
	 * testset ==> name
	 * @throws Exception
	 */
	public void getMandatoryFieldsInEntity() throws Exception {
		AlmConnector alm = new AlmConnector();

		try {	
			RestConnector conn = RestConnector.getInstance();
			conn.init(new HashMap<String, String>(), Constants.HOST + ":"
					+ Constants.PORT + "/qcbin", Constants.DOMAIN,
					Constants.PROJECT);
			alm.createSession(Constants.USERNAME, Constants.PASSWORD);

			String url = conn.buildUrl("rest/domains/"
	                + Constants.DOMAIN
	                + "/projects/"
	                + Constants.PROJECT
	                + "/customization/entities/run/fields"
//	                + "?required=true"
	                );

			Map<String, String> requestHeaders = new HashMap<String, String>();
			requestHeaders.put("Accept", "application/json");
			requestHeaders.put("Content-Type", "application/json");

			Response res = conn.httpGet(url, null, requestHeaders);
			if(res.getStatusCode() == HttpURLConnection.HTTP_OK || res.getStatusCode() == HttpURLConnection.HTTP_CREATED) {
				String postedEntityReturnedXml = res.toString();
				System.out.println(postedEntityReturnedXml);		
			} else {
				System.out.println(res.toString());
				throw res.getFailure();
			}
		} finally  {
			alm.logout();
			alm = null;
		}
	}
	
	public void testGetTestRunsInTestLab() throws Exception {
		AlmConnector alm = new AlmConnector();

		try {	
			RestConnector conn = RestConnector.getInstance();
			conn.init(new HashMap<String, String>(), Constants.HOST + ":"
					+ Constants.PORT + "/qcbin", Constants.DOMAIN,
					Constants.PROJECT);
			alm.createSession(Constants.USERNAME, Constants.PASSWORD);

			String url = conn.buildEntityCollectionUrl("test-instance");
//						url += "/93";

			Map<String, String> requestHeaders = new HashMap<String, String>();
			requestHeaders.put("Accept", "application/json");

			Response res = conn.httpGet(url, null, requestHeaders);
			if(res.getStatusCode() == HttpURLConnection.HTTP_OK || res.getStatusCode() == HttpURLConnection.HTTP_CREATED) {
				String postedEntityReturnedXml = res.toString();
				System.out.println(postedEntityReturnedXml);		
			} else {
				System.out.println(res.toString());
				throw res.getFailure();
			}
		} finally  {
			alm.logout();
			alm = null;
		}
	}
}
