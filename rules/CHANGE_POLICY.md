# Change Policy
Version: 1.0.0

## LOW RISK — běžná změna

Příklady:
- oprava překlepu,
- doplnění komentáře,
- izolovaná implementace podle schváleného návrhu,
- doplnění testu bez změny produkčního chování.

Agent může pokračovat v rámci schváleného zadání.
Musí uvést provedené změny a výsledky kontrol.

## MEDIUM RISK — změna vyžadující kontrolu

Příklady:
- změna více modulů,
- změna veřejných metod,
- změna transakční logiky,
- změna databázových dotazů,
- přidání nebo změna konfigurace aplikace.

Agent nejprve předloží plán a rozsah změn.
Pokračuje podle schvalovacího režimu projektu.

## HIGH RISK — povinné schválení před akcí

Příklady:
- změna architektury,
- změna bezpečnostního modelu,
- změna autentizace a autorizace,
- změna databázového schématu,
- přidání externí závislosti,
- odstranění existující funkcionality,
- destruktivní příkazy,
- nasazení nebo publikace artefaktů.

Bez schválení se tato akce neprovede.

## Neznámé riziko

Pokud agent neumí riziko spolehlivě klasifikovat,
považuje změnu za HIGH RISK.

## Způsob schválení

Schválení musí být jednoznačné a vztahovat se
ke konkrétnímu plánu a rozsahu změn.

Změna plánu vyžaduje nové schválení,
pokud mění riziko nebo rozsah povolených zásahů.