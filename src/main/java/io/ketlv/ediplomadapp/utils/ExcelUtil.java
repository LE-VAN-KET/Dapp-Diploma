package io.ketlv.ediplomadapp.utils;

import io.ketlv.ediplomadapp.domain.Diploma;
import io.ketlv.ediplomadapp.domain.DiplomaType;
import io.ketlv.ediplomadapp.enumuration.DiplomaStatusEnum;
import io.ketlv.ediplomadapp.enumuration.ModeOfStudyEnum;
import io.ketlv.ediplomadapp.enumuration.SexEnum;
import io.ketlv.ediplomadapp.mapper.DiplomaTypeMapper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.Year;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class ExcelUtil {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    @Autowired
    private DiplomaTypeMapper diplomaTypeMapper;

    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public List<Diploma> excelToDiplomas(InputStream is, String SHEET, String schoolSymbol, String graduationCatalogId) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Diploma> diplomas = new ArrayList<>();
            String diplomaType = null;

            int rowNumber = 0;
            boolean diplomatypeSymbolFlg = false;
            boolean rowBreak = false;
            String diplomatypeSymbol = null;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                if (rowBreak) {
                    break;
                }

                Iterator<Cell> cellsInRow = currentRow.cellIterator();

                Diploma diploma = new Diploma();
                diploma.setDonviSymbol(schoolSymbol);

                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    if (currentCell.getColumnIndex() == 0) {
                        diplomaType = currentCell.getStringCellValue();
                        if (!diplomatypeSymbolFlg) {
                            DiplomaType diplomaTypeExist = diplomaTypeMapper.findOneByName(diplomaType);
                            if (diplomaTypeExist != null) {
                                diploma.setDiplomaTypeSymbol(diplomaTypeExist.getSymbol());
                                diplomatypeSymbol = diplomaTypeExist.getSymbol();
                                diplomatypeSymbolFlg= true;
                            }
                        } else {
                            diploma.setDiplomaTypeSymbol(diplomatypeSymbol);
                        }
                    }

                    if (currentCell.getColumnIndex() == 1 && ("".equals(currentCell.getStringCellValue()) || currentCell.getStringCellValue() == null)) {
                        rowBreak = true;
                        break;
                    }

                    switch (currentCell.getColumnIndex()) {
                        case 1:
                            diploma.setMajorId(currentCell.getStringCellValue());
                            break;
                        case 3:
                            diploma.setStudentId(currentCell.getStringCellValue());
                            break;
                        case 4:
                            diploma.setFullName(currentCell.getStringCellValue());
                            break;
                        case 5:
                            diploma.setDateOfBirth(currentCell.getDateCellValue());
                            break;
                        case 6:
                            diploma.setPlaceOfOrigin(currentCell.getStringCellValue());
                            break;
                        case 7:
                            String sex = currentCell.getStringCellValue();
                            if ("Nam".equals(sex)) {
                                diploma.setSex(SexEnum.MALE);
                            } else {
                                diploma.setSex(SexEnum.FERMALE);
                            }
                            break;
                        case 8:
                            diploma.setIndigenousId(Long.parseLong(currentCell.getStringCellValue()));
                            break;
                        case 10:
                            diploma.setNationalityId(Long.parseLong(currentCell.getStringCellValue()));
                            break;
                        case 12:
                            diploma.setYearGraduation(Integer.parseInt(currentCell.getStringCellValue()));
                            break;
                        case 14:
                            diploma.setRankingId(Long.parseLong(currentCell.getStringCellValue()));
                            break;
                        case 15:
                            String mode = currentCell.getStringCellValue();
                            if (ModeOfStudyEnum.CHINH_QUY.getValue().equals(mode)) {
                                diploma.setModeOfStudy(ModeOfStudyEnum.CHINH_QUY);
                            } else {
                                diploma.setModeOfStudy(ModeOfStudyEnum.TAI_CHUC);
                            }
                            break;
//                        case 16:
//                            String serialNumber = currentCell.getStringCellValue();
//                            if (!isValidSerialNumber(serialNumber, diplomaType)) {
//                                throw new RuntimeException(String.format("[Row: %d, Cell: %d] Serial Number format is incorrect!",
//                                        currentCell.getRowIndex(), currentCell.getColumnIndex()));
//                            }
//                            diploma.setSerialNumber(serialNumber);
//                            break;
                        case 17:
                            String refNumber = currentCell.getStringCellValue();
                            if (!isValidReferenceNumber(refNumber, diploma.getDiplomaTypeSymbol())) {
                                throw new RuntimeException(String.format("[Row: %d, Cell: %d] Reference Number format is incorrect!",
                                        currentCell.getRowIndex(), currentCell.getColumnIndex()));
                            }
                            diploma.setReferenceNumber(refNumber);
                            break;
                        case 18:
                            diploma.setSigner(currentCell.getStringCellValue());
                            break;
                        case 19:
                            diploma.setSignerTitle(currentCell.getStringCellValue());
                            break;
                        case 24:
                            if (!"".equals(currentCell.getStringCellValue())) {
                                diploma.setTrainingCourse(Integer.valueOf(currentCell.getStringCellValue()));
                            }
                            break;
                        case 27:
                            diploma.setDecisionNumber(currentCell.getStringCellValue());
                            break;
                        case 29:
                            diploma.setReqTypeId(Long.parseLong(currentCell.getStringCellValue()));
                            break;
                        case 32:
                            diploma.setNote(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                }
                diploma.setGraduationCatalogId(Long.parseLong(graduationCatalogId));
                diploma.setStatus(DiplomaStatusEnum.PENDING);
                diplomas.add(diploma);
            }

            workbook.close();
//            if (diplomaType != null) {
//                DiplomaType diplomaTypeExist = diplomaTypeMapper.findOneByName(diplomaType);
//                if (diplomaTypeExist != null) {
//                    for (Diploma dip: diplomas) {
//                        dip.setDiplomaTypeSymbol(diplomaTypeExist.getSymbol());
//                    }
//                } else {
//                    diplomas.clear();
//                }
//            }
            return diplomas;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    private boolean isValidSerialNumber(String serialNumber, String diplomaTypeSymbol) {
        String PATTERN = "^[A-Z]{3}\\.(" + diplomaTypeSymbol + "){1}\\.(\\d)+";
        Pattern pattern = Pattern.compile(PATTERN);
        return pattern.matcher(serialNumber).matches();
    }

    private boolean isValidReferenceNumber(String referenceNumber, String diplomaTypeSymbol) {
        String currentYear = Year.now().toString();
        String PATTERN = "^[A-Z]{3}\\.(" + diplomaTypeSymbol + "){1}\\.(\\d)+\\.(" +
                currentYear.substring(currentYear.length() - 2, currentYear.length()) + ")";
        Pattern pattern = Pattern.compile(PATTERN);
        return pattern.matcher(referenceNumber).matches();
    }
}
