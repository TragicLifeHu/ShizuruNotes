package com.github.nyanfantasia.shizurunotes.data.action;

import com.github.nyanfantasia.shizurunotes.R;
import com.github.nyanfantasia.shizurunotes.common.I18N;
import com.github.nyanfantasia.shizurunotes.data.Property;
import com.github.nyanfantasia.shizurunotes.data.PropertyKey;
import com.github.nyanfantasia.shizurunotes.utils.Utils;

public class DamageAction extends ActionParameter {

    protected ClassModifier damageClass;
    protected CriticalModifier criticalModifier;
    protected DecideTargetAtkType decideTargetAtkType;

    enum DecideTargetAtkType{
        bySource(0),
        lowerDef(1);

        private int value;
        DecideTargetAtkType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static DamageAction.DecideTargetAtkType parse(int value){
            for(DamageAction.DecideTargetAtkType item : DamageAction.DecideTargetAtkType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return bySource;
        }
    }

    @Override
    protected void childInit() {
        damageClass = ClassModifier.parse(actionDetail1);
        criticalModifier = CriticalModifier.parse((int)actionValue5.value);
        decideTargetAtkType = DecideTargetAtkType.parse(actionDetail2);

        switch (damageClass) {
            case magic:
                actionValues.add(new ActionValue(actionValue1, actionValue2, null));
                actionValues.add(new ActionValue(actionValue3, actionValue4, PropertyKey.MagicStr));
                break;
            case physical:
            case inevitablePhysical:
                actionValues.add(new ActionValue(actionValue1, actionValue2, null));
                actionValues.add(new ActionValue(actionValue3, actionValue4, PropertyKey.Atk));
                break;
            default:
        }
    }

    @Override
    public String localizedDetail(int level, Property property) {
        StringBuilder string = new StringBuilder();
        switch (criticalModifier) {
            case normal:
                string.append(I18N.getString(R.string.Deal_s1_s2_damage_to_s3, buildExpression(level, property), damageClass.description(), targetParameter.buildTargetClause()));
                break;
            case critical:
                string.append(I18N.getString(R.string.Deal_s1_s2_damage_to_s3_and_this_attack_is_ensured_critical, buildExpression(level, property), damageClass.description(), targetParameter.buildTargetClause(), Utils.roundIfNeed(actionValue5.value)));
                break;
        }
        if (actionValue6.value != 0) {
            string.append(I18N.getString(R.string.Critical_damage_is_s_times_as_normal_damage, 2 * actionValue6.value));
        }
        if (decideTargetAtkType == DecideTargetAtkType.lowerDef) {
            string.append(I18N.getString(R.string.This_damage_type_is_judged_by_the_lower_defence_value_of_targeted_enemy));
        }
        return string.toString();
    }
}
