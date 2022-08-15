package com.github.nyanfantasia.shizurunotes.utils

object KanaUtils {
    private fun String.replacePair(vararg replacements: Pair<String, String>): String {
        var result = this
        replacements.forEach { (l, r) -> result = result.replace(l, r) }
        return result
    }

    fun katakana2Romaji(string: String): String {
        return string.replacePair(
            "＆" to "&",
            "　" to " ",
            "（" to "(",
            "）" to ")",
            "ー" to "",

            // 外來音
            "イェ" to "ye",
            "ウィ" to "wi", "ウェ" to "we", "ウォ" to "wo",
            "ヴ" to "vu", "ヴァ" to "va", "ヴィ" to "vi", "ヴェ" to "ve", "ヴォ" to "vo", "ヴュ" to "vyu", "ヴョ" to "vyo",
            "キェ" to "kya",
            "クァ" to "kwa", "クィ" to "kwi", "クェ" to "kwe", "クォ" to "kwo",
            "グァ" to "gwa", "グィ" to "gwi", "グェ" to "gwe", "グォ" to "gwo",

            // 外來音
            "シェ" to "she",
            "ジェ" to "je",
            "チェ" to "che",
            "ツァ" to "tsa", "ツィ" to "tsi", "ツェ" to "tse", "ツォ" to "tso",
            "ティ" to "ti", "テュ" to "tyu",
            "ディ" to "di", "デュ" to "dyu",
            "トゥ" to "tu",
            "ドゥ" to "du",
            "ニェ" to "nye",
            "ヒェ" to "hye",
            "ファ" to "fa", "フィ" to "fi", "フェ" to "fe", "フォ" to "fo", "フュ" to "fyu", "フョ" to "fyo",


            "ギャ" to "gya", "ギュ" to "gyu", "ギョ" to "gyo",
            "ジャ" to "ja", "ジュ" to "ju", "ジョ" to "jo",
            "ヂャ" to "ja", "ヂュ" to "ju", "ヂョ" to "jo",
            "ビャ" to "bya", "ビュ" to "byu", "ビョ" to "byo",
            "ピャ" to "pya", "ピュ" to "pyu", "ピョ" to "pyo",

            "キャ" to "kya", "キュ" to "kyu", "キョ" to "kyo",
            "シャ" to "sha", "シュ" to "shu", "ショ" to "sho",
            "チャ" to "cha", "チュ" to "chu", "チョ" to "cho",
            "ニャ" to "nya", "ニュ" to "nyu", "ニョ" to "nyo",
            "ヒャ" to "hya", "ヒュ" to "hyu", "ヒョ" to "hyo",
            "ミャ" to "mya", "ミュ" to "myu", "ミョ" to "myo",
            "リャ" to "rya", "リュ" to "ryu", "リョ" to "ryo",

            "ァ" to "a", "ィ" to "i", "ゥ" to "u", "ェ" to "e", "ォ" to "o",
            "ャ" to "ya", "ュ" to "yu", "ョ" to "yo",
            "ヮ" to "wa", "ヵ" to "ka", "ヶ" to "ke", "ン" to "n",

            "ア" to "a", "イ" to "i", "ウ" to "u", "エ" to "e", "オ" to "o",
            "カ" to "ka", "キ" to "ki", "ク" to "ku", "ケ" to "ke", "コ" to "ko",
            "サ" to "sa", "シ" to "shi", "ス" to "su", "セ" to "se", "ソ" to "so",
            "タ" to "ta", "チ" to "chi", "ツ" to "tsu", "テ" to "te", "ト" to "to",
            "ナ" to "na", "ニ" to "ni", "ヌ" to "nu", "ネ" to "ne", "ノ" to "no",
            "ハ" to "ha", "ヒ" to "hi", "フ" to "fu", "ヘ" to "he", "ホ" to "ho",
            "マ" to "ma", "ミ" to "mi", "ム" to "mu", "メ" to "me", "モ" to "mo",
            "ヤ" to "ya", "ユ" to "yu", "ヨ" to "yo",
            "ラ" to "ra", "リ" to "ri", "ル" to "ru", "レ" to "re", "ロ" to "ro",
            "ワ" to "wa", "ヰ" to "i", "ヱ" to "e", "ヲ" to "o",

            "ガ" to "ga", "ギ" to "gi", "グ" to "gu", "ゲ" to "ge", "ゴ" to "go",
            "ザ" to "za", "ジ" to "ji", "ズ" to "zu", "ゼ" to "ze", "ゾ" to "zo",
            "ダ" to "da", "ヂ" to "ji", "ヅ" to "zu", "デ" to "de", "ド" to "do",
            "バ" to "ba", "ビ" to "bi", "ブ" to "bu", "ベ" to "be", "ボ" to "bo",
            "パ" to "pa", "ピ" to "pi", "プ" to "pu", "ペ" to "pe", "ポ" to "po",
        )
    }

    fun katakana2English(string: String): String {
        val priconnedString = string.replacePair(
            // プリコネ
            "サマー" to "summer",
            "ハロウィン" to "halloween",
            "クリスマス" to "christmas",
            "ニューイヤー" to "new year",
            "バレンタイン" to "valentine",
            "オーエド" to "oedo",
            "編入生" to "student",
            "マジカル" to "magical",
            "レンジャー" to "ranger",
            "エンジェル" to "angel",
            "ワンダー" to "wonder",
            "シンデレラ" to "cinderella",
            "タイムトラベル" to "time travel",
            "ノワール" to "noir",
            "オーバーロード" to "overload",
            "ステージ" to "stage",
            "怪盗" to "phantom",
            "パイレーツ" to "pirate",
            "キャンプ" to "camp",
            "プリンセス" to "princess",
            // プリコネ
            "クリスティーナ" to "christina",
            "アリサ" to "alisa",
            "シェフィ" to "shefi",
            "ペコリーヌ" to "pecorine",
            "コッコロ" to "kokkoro",
            "ジータ" to "djeeta",
            "アンナ" to "anna",
            "アン" to "anne",
            "クロエ" to "chloe",
            "ラビリスタ" to "labyrista",
            "イリヤ" to "ilya",
            "クレジッタ" to "creditta",
            "ランファ" to "ranpha",
            "ルナ" to "luna",
            "ルゥ" to "lou",
            "グレア" to "grea",
            "エミリア" to "emilia",
            "アメス" to "ames",
        )
        return katakana2Romaji(priconnedString)
    }
}