package io.ketlv.ediplomadapp.domain;

import io.ketlv.ediplomadapp.enumuration.BacHocEnum;
import io.ketlv.ediplomadapp.enumuration.LoaiPhoiEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Phoi extends AbstractAuditingEntity<Long> {
    private Long id;
    private String title;
    private String donviSymbol;
    private String diplomaTypeSymbol;
    private int amountOldStock = 0;
    private int amountIssuedNewPrint;
    private int amountBroken = 0;
    private int amountIssuedStudent = 0;
    private int amountUnused = 0;
    private String serialNumberBegin;
    private String serialNumberEnd;
    private LoaiPhoiEnum loaiPhoi;
    private Date ngayMua;
    private Date ngayNhapKho;
    private String note;

    @Override
    public Long getId() {
        return id;
    }

    public String getDonviSymbol() {
        return donviSymbol;
    }

    public String getDiplomaTypeSymbol() {
        return diplomaTypeSymbol;
    }

    public int getAmountOldStock() {
        return amountOldStock;
    }

    public int getAmountIssuedNewPrint() {
        return amountIssuedNewPrint;
    }

    public int getAmountBroken() {
        return amountBroken;
    }

    public int getAmountIssuedStudent() {
        return amountIssuedStudent;
    }

    public int getAmountUnused() {
        return amountUnused;
    }

    public String getSerialNumberBegin() {
        return serialNumberBegin;
    }

    public String getSerialNumberEnd() {
        return serialNumberEnd;
    }


    public LoaiPhoiEnum getLoaiPhoi() {
        return loaiPhoi;
    }

    public Date getNgayMua() {
        return ngayMua;
    }

    public Date getNgayNhapKho() {
        return ngayNhapKho;
    }

    public String getNote() {
        return note;
    }

    public String getTitle() {
        return title;
    }
}
