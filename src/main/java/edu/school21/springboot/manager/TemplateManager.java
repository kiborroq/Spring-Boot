package edu.school21.springboot.manager;

import java.util.Map;

public interface TemplateManager {

	String renderHtml(String templateName, Map<String, Object> templateData);

}
