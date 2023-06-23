create function revokePhoi(phoi_id bigint)
    returns void
    language plpgsql
as
$$
declare
    serial_begin VARCHAR;
    serial_end VARCHAR;
begin
    select serial_number_begin, serial_number_end  into serial_begin, serial_end
    from phoi
    where id=phoi_id;

    update diploma set serial_number=NULL where serial_number >= serial_begin and
            serial_number <= serial_end;

    delete from phoi where id=phoi_id;
end;
$$;