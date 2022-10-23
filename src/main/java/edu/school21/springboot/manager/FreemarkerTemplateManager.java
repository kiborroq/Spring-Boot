package edu.school21.springboot.manager;

import edu.school21.springboot.exception.CinemaRuntimeException;
import freemarker.core.HTMLOutputFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
class FreemarkerTemplateManager implements TemplateManager {

	private final Configuration configuration;

	public FreemarkerTemplateManager() {
		configuration = new Configuration(Configuration.VERSION_2_3_23);
		configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
		configuration.setLogTemplateExceptions(false);
		configuration.setOutputFormat(HTMLOutputFormat.INSTANCE);
		configuration.setClassLoaderForTemplateLoading(getClass().getClassLoader(), "files/templates");
	}

	@Override
	public String renderHtml(String templateName, Map<String, Object> templateData) {
		try (StringWriter writer = new StringWriter()) {
			Template template = configuration.getTemplate(templateName);
			template.process(templateData, writer);
			return writer.getBuffer().toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CinemaRuntimeException(String.format("Error during html %s render", templateName), e);
		}
	}
}
