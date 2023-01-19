package br.gov.caixa.negociosdigitais.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class UtilsToData {


    private UtilsToData() {
    }

    public static String formatDataHoraParaString(Date data) {
        DateFormat formatar = new SimpleDateFormat("yyyy-MM-dd");
        if (Objects.isNull(data)) {
            return null;
        }
        return formatar.format(data);
    }

    public static Date formatStringParaDataHora(String data) throws ParseException {
        DateFormat formatar = new SimpleDateFormat("yyyy-MM-dd");
        if (Objects.isNull(data)) {
            return null;
        }
        return formatar.parse(data);
    }
}
