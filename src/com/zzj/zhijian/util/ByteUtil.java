package com.zzj.zhijian.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ByteUtil {
	public static InputStream workbook2InputStream(HSSFWorkbook workbook)
			throws Exception
	{
		InputStream excelStream=null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		workbook.write(baos);
		baos.flush();
		byte[] aa = baos.toByteArray();
		excelStream = new ByteArrayInputStream(aa, 0, aa.length);
		baos.close();
		return excelStream;
	}

}
