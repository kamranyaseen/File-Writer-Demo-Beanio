package com.writer.report;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.writer.pojo.Details;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import net.sf.jasperreports.components.table.Column;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class DetailsReport {

	private final List<ReportData> list1 = new ArrayList<>();

	public DetailsReport(List<Details> c) {
		List<ReportData> list = new ArrayList<>();
		for (Details d : c) {
			ReportData data = new ReportData();
			String amount = d.getAmount().replaceAll("[a-zA-Z]", "");
			data.setDate(null);
			data.setReferenceNo(d.getReferenceNo());
			data.setAmount(BigDecimal.valueOf(Double.parseDouble(amount)));
			data.setStatus(null);
			data.setRemark(null);
			list.add(data);
		}
		list1.addAll(list);
	}

	public JasperPrint getReport() throws ColumnBuilderException, JRException, ClassNotFoundException {
		Style headerStyle = createHeaderStyle();
		Style detailTextStyle = createDetailTextStyle();
		Style detailNumberStyle = createDetailNumberStyle();
		DynamicReport dynaReport = getReport(headerStyle, detailTextStyle,detailNumberStyle);
		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dynaReport, new ClassicLayoutManager(),
				new JRBeanCollectionDataSource(list1));
		return jp;
	}

	public Style createHeaderStyle() {
		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(Font.VERDANA_MEDIUM_BOLD);
		sb.setBorder(Border.THIN());
		sb.setBorderBottom(Border.PEN_2_POINT());
		sb.setBorderColor(Color.BLACK);
		sb.setTextColor(Color.BLACK);
		sb.setTransparency(Transparency.OPAQUE);
		return sb.build();
	}

	private Style createDetailTextStyle() {
		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(Font.VERDANA_MEDIUM);
		sb.setBorder(Border.DOTTED());
		sb.setBorderColor(Color.BLACK);
		sb.setTextColor(Color.BLACK);
		sb.setPaddingLeft(5);
		return sb.build();
	}

	private Style createDetailNumberStyle() {
		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(Font.VERDANA_MEDIUM);
		sb.setBorder(Border.DOTTED());
		sb.setBorderColor(Color.BLACK);
		sb.setTextColor(Color.BLACK);
		sb.setPaddingRight(5);
		return sb.build();
	}

	private AbstractColumn createColumn(String property, Class type, String title, int width, Style headerStyle,
			Style detailStyle) throws ColumnBuilderException {
		AbstractColumn columnState = ColumnBuilder.getNew().setColumnProperty(property, type.getName()).setTitle(title)
				.setWidth(Integer.valueOf(width)).setStyle(detailStyle).setHeaderStyle(headerStyle).build();
		return columnState;
	}

	private DynamicReport getReport(Style headerStyle, Style detailTextStyle, Style detailNumStyle)
			throws ColumnBuilderException, ClassNotFoundException {

		DynamicReportBuilder report = new DynamicReportBuilder();

		AbstractColumn columnEmpNo = createColumn("date", String.class, "Date", 30, headerStyle, detailTextStyle);
		AbstractColumn columnName = createColumn("referenceNo", String.class, "Reference no", 30, headerStyle,
				detailTextStyle);
		AbstractColumn columnSalary = createColumn("amount", BigDecimal.class, "Amount", 30, headerStyle, detailNumStyle);
		AbstractColumn columnCommission = createColumn("status", String.class, "status", 30, headerStyle, detailNumStyle);
		AbstractColumn columnRemark = createColumn("remark", String.class, "remarks", 30, headerStyle, detailNumStyle);
		report.addColumn(columnEmpNo).addColumn(columnName).addColumn(columnSalary).addColumn(columnCommission)
				.addColumn(columnRemark);

		report.setUseFullPageWidth(true);
		return report.build();
	}
}
