package io.ketlv.ediplomadapp.services.dto;

import io.ketlv.ediplomadapp.enumuration.BacHocEnum;
import io.ketlv.ediplomadapp.enumuration.LoaiPhoiEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PhoiDtoReq {
    @NotNull
    @NotEmpty(message = "tiêu đề không được bỏ trống!")
    private String title;
    private String donviSymbol;
    private String diplomaTypeSymbol;
    private LoaiPhoiEnum loaiPhoi;
    private Date ngayMua;
    @Min(1)
    private Integer soluong;
    private Date ngayNhapKho;
    private String note;
}
