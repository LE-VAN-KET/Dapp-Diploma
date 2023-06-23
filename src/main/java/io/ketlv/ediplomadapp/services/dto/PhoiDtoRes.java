package io.ketlv.ediplomadapp.services.dto;

import io.ketlv.ediplomadapp.domain.AbstractAuditingEntity;
import io.ketlv.ediplomadapp.enumuration.BacHocEnum;
import io.ketlv.ediplomadapp.enumuration.LoaiPhoiEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class PhoiDtoRes extends AbstractAuditingEntity<Long> {
    private Long id;
    private String title;
    private String donvi;
    private String diplomaType;
    private int amountOldStock;
    private int amountIssuedNewPrint;
    private int amountBroken;
    private int amountIssuedStudent;
    private int amountUnused;
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

    public String getTitle() {
        return title;
    }

    public String getDonvi() {
        return donvi;
    }

    public String getDiplomaType() {
        return diplomaType;
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
}
