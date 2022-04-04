package com.example.cognitive.Bean;

public class AdviceItem
{
//    private int healthScore;
//    private int strengthScore;
//    private int cognitionScore;
//    private int memoryScore;
//    private int judgementScore;
    private String attribute;
    private String advice;
    private String level;
    public AdviceItem() {
    }

    public AdviceItem(String attr, int score) {
        this.attribute=attr;
        //health
        if (attribute.equals("健康")) {
            if (score == 2) {
                this.advice = "根据您填写的信息，您目前患有多种慢性病，健康状况不理想，需要及早干预就医，遵循医嘱，保持良好的作息习惯和心态，将病情控制在最小伤害上。";
                this.level="极低";
            } else if (score== 1) {
                this.advice = "根据您填写的信息，您现在身体健康状况一般，各类慢性病需要长期的调理，坚持健康的作息和保持良好的心态是走向健康的第一步。";
                this.level="偏低";
            } else {
                this.advice = "根据您填写的信息，您现在身体十分健康，没有基础疾病，请继续保持~";
                this.level="正常";
            }
        }
        //strength
        if (attribute.equals("体力")) {
            if (score == 0) {
                this.advice = "太棒了！您的体力处于正常值，请继续保持，良好的习惯需要不断地坚持。";
                this.level="正常";
            } else if (score== 1) {
                this.advice = "根据您的回答，您现在的体力偏低，可以进一步通过运动来恢复到正常状态。在运动时要注意做好热身和拉伸，推荐您选择慢跑、羽毛球、登山等活动，和家人一起享受健康生活。";
                this.level="偏低";
            } else {
                this.advice = "根据您的回答显示，您目前的体力极低，在运动的持续时间和耐力上下降较多，推荐您尝试饭后散步和太极运动来增加活动量。体力下降是不可避免的，但是我们可以通过自身的力量减缓它！";
                this.level="极低";
            }
        }
        //cognition
        if (attribute.equals("意识")) {
            if (score == 0) {
                this.advice = "根据您的回答，您现在思维意识十分清晰，请继续保持。";
                this.level="正常";
            } else if (score == 1) {
                this.advice = "根据您的回答，您偶尔会在处理较复杂的事务上失误，这些是可以通过运动和饮食来改善的。您可以尝试阻抗练习（力量练习），阻抗练习是指人体克服外界阻力进行的主动练习，阻力的大小可以根据自身力量进行调节，如俯卧撑、蹲起、器械运动(卧推、深蹲、沙袋)等都是阻力练习内容。科学研究显示，这类运动能够从不同的方面提升大脑的认知效果。";
                this.level="偏低";
            } else {
                this.advice = "根据您的回答，您现在在处理复杂事物上有困难，科学研究显示，球类运动对大脑有显著的锻炼效果，您可以尝试乒乓球，除此之外，太极和瑜伽也能够调整身体状况，增强注意力。";
                this.level="极低";
            }
        }
        //memory
        if(attribute.equals("记忆")) {
            if (score == 2) {
                this.advice = "根据您的回答，您现在的记忆力处于较低的水平。但是，记忆力是可以训练出来的，您可以选择读书看报，做一些笔记并转述给家人听，多主动回忆前几天发生的重要的事情。行动起来，记忆力是可以锻炼出来的。";
                this.level="极低";
            } else if (score == 1) {
                this.advice = "根据您的回答，您现在的记忆力偏低。记忆力训练不言迟，您可以在桌上摆三四件小物品，如瓶子、纸盒、钢笔、书等，对每件物品进行追踪思考各种两分钟，即在两分钟内思考某件物品的一系列有关内容。例如思考瓶子时，想到各种各样的瓶子，想到各种瓶子的用途，想到瓶子时，想到各种各样的制造，造玻璃的矿石来源等。这时，控制自己不想别的物品。两分钟后，立即把注意力转移到第二件物品上。开始时，较难做到两分钟后的迅速转移，但如果每天练习10分钟，两周后情况就大有好转了。";
                this.level="偏低";
            } else {
                this.advice = "根据您的回答，您现在记忆力很好，请继续保持。";
                this.level="正常";
            }
        }
        //judgement
        if(attribute.equals("判断")) {
            if (score == 1) {
                this.advice = "根据您的回答，您现在的判断力偏低。您可以进行一些基础训练，如阅读，看报等，阅读过程要多思考、勤做笔记；除此之外多和家人，邻居聊天交流，谈论身边的事，听取不同的意见也有助于恢复判断力。";
                this.level="偏低";
            } else {
                this.advice = "根据您的回答，您现在的判断力良好，请继续保持。";
                this.level="正常";
            }
        }
    }

    public String getAdvice() {
        return advice;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getLevel() {
        return level;
    }
}
