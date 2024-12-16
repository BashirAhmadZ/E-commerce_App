package com.shopme.admin.product;

import java.io.IOException;
import java.util.List;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.shopme.admin.AbstractExporter;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.product.Product;

import jakarta.servlet.http.HttpServletResponse;

public class ProductCsvExporter extends AbstractExporter {
	public void export(List<Product> listProducts, HttpServletResponse response) 
			throws IOException {
		super.setResponseHeader(response, "text/csv", ".csv",  "products_");
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
				CsvPreference.STANDARD_PREFERENCE);
		
		String[] csvHeader = {"Product ID", "Products Name", "Description", "Created Time", "Updated Time", "Enabled", "In-Stock", "Price"};
		String[] fieldMapping = {"id", "name", "shortDescription", "createdTime", "updatedTime", "enabled", "inStock", "price"};
		
		csvWriter.writeHeader(csvHeader);
		
		for(Product product : listProducts) {
			csvWriter.write(product, fieldMapping);
		}
		
		csvWriter.close();
	}
}
