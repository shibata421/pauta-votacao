package br.com.shibata.fernando.application.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VotoEnum {

    SIM("S"),
    NAO("N");

    private final String descricao;

    public static boolean votoCorreto(String s) {
        for (VotoEnum v : values()) {
            if (v.descricao.equals(s)) {
                return true;
            }
        }

        return false;
    }
}
