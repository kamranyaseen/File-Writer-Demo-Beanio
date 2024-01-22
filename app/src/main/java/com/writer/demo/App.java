/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.writer.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.*;
import org.apache.commons.lang3.StringUtils;
import org.beanio.BeanWriter;
import org.beanio.StreamFactory;
import org.beanio.builder.FixedLengthParserBuilder;
import org.beanio.builder.StreamBuilder;

import com.writer.pojo.Details;
import com.writer.pojo.Header;
import com.writer.pojo.Trailer;
import com.writer.report.DetailsReport;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class App {

	public static void main(String[] args) {

		try {
			if (args[0] == null || args[0].trim().isEmpty()) {
				System.out.println("You need to specify a path!");
				return;
			} else {

				Path path = Paths.get(args[0]);

				if (Files.notExists(path)) {
					System.out.println("File not found/wrong path!");
					return;
				} else {

				}
				File file = new File(args[0]);

				List<Details> lines = strReader(file);

				StreamFactory factory = StreamFactory.newInstance();

				StreamBuilder buildercsv = new StreamBuilder("dt").format("fixedlength")
						.parser(new FixedLengthParserBuilder()).addRecord(com.writer.pojo.Header.class)
						.addRecord(com.writer.pojo.Details.class).addRecord(com.writer.pojo.Trailer.class);
				factory.define(buildercsv);

				BeanWriter out = factory.createWriter("dt", new File("c:\\tm.txt"));

				Header head = new Header();
				Trailer trailer = new Trailer();
				head.setRecordType("H");
				head.setFileType("FLAT_FILE");

				trailer.setRecordType("T");
				trailer.setFileType("FLAT_FILE");

				out.write(head);
				for (Details d : lines) {
					out.write(d);
				}
				out.write(trailer);

				out.flush();
				out.close();
				
				// report
				DetailsReport report = new DetailsReport(lines);

				JasperPrint jp = report.getReport();
				JasperViewer jasperViewer = new JasperViewer(jp);
				jasperViewer.setVisible(true);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("file write successfully.");
	}

	public static List<Details> strReader(File in) {
		List<Details> dt = new ArrayList<Details>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(in));
			String line;
			while ((line = br.readLine()) != null) {
				String[] names = StringUtils.split(line, ":,");
				Details details = new Details();
				details.setReferenceNo(names[1].trim().length() == 0 ? " no ref" : names[1]);
				details.setAmount(names[2].trim().length() == 0 ? " RM0.00" : names[2]);
				dt.add(details);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dt;
	}
}