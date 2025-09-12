package com.sergei.pokhodai.expensemanagement.feature.exchangerate.api.domain.model

import com.sergei.pokhodai.expensemanagement.core.formatter.LocalDateFormatter
import java.math.BigDecimal

data class CbrDailyModel(
    val date: LocalDateFormatter?,
    val previousDate: LocalDateFormatter?,
    val valuteModelList: List<ValuteModel>
) {
    data class ValuteModel(
        val id: String,
        val numCode: String,
        val charCode: CharCode,
        val nominal: Int,
        val name: String,
        val value: BigDecimal,
        val previousValue: BigDecimal,
    )

    enum class CharCode(val order: Int) {
        USD(1),
        EUR(2),
        BYN(3),
        KZT(4),
        AMD(5),
        TRY(6),
        AUD(7),
        AZN(8),
        DZD(9),
        GBP(10),
        BHD(11),
        BGN(12),
        BOB(13),
        BRL(14),
        HUF(15),
        VND(16),
        HKD(17),
        GEL(18),
        DKK(19),
        AED(20),
        EGP(21),
        IDR(22),
        IRR(23),
        CAD(24),
        QAR(25),
        KGS(26),
        CNY(27),
        CUP(28),
        MDL(29),
        MNT(30),
        NZD(31),
        NOK(32),
        OMR(33),
        PLN(34),
        SAR(35),
        RON(36),
        XDR(37),
        SGD(38),
        TJS(39),
        THB(40),
        BDT(41),
        TMT(42),
        UZS(43),
        UAH(44),
        CZK(45),
        SEK(46),
        CHF(47),
        ETB(48),
        RSD(49),
        ZAR(50),
        KRW(51),
        JPY(52),
        MMK(53)
    }
}