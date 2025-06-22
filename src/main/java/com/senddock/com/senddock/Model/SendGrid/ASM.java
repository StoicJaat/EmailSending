package com.senddock.com.senddock.Model.SendGrid;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ASM {
    private int group_id;

    public ASM(int group_id) {
        this.group_id = group_id;
    }
    public ASM() {
    }

}
