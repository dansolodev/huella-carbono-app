package com.fs.huellacarbonoapp.utils

/**
 * Factores de emisión (kg CO2e por unidad)
 * - Electricidad MX: kg CO2e / kWh
 * - Gasolina:       kg CO2e / L
 * - Gas LP:         kg CO2e / kg
 */
object EmissionFactors {
    const val ELECTRICITY_MX_KG_PER_KWH = 0.455
    const val GASOLINE_KG_PER_LITER = 2.31
    const val GASOLINE_EFFICIENCY_KM_PER_LITER = 12.0
    const val LPG_KG_PER_KG = 3.02
}

/**
* ELECTRICIDAD
* Emisiones = kWh * factor (kg CO2e/kWh)
*/
fun electricityEmissionsKgCO2e(
    kWh: Double,
    factorKgPerKWh: Double = EmissionFactors.ELECTRICITY_MX_KG_PER_KWH
): Double = kWh * factorKgPerKWh

/**
 * GASOLINA (si conoces los KILÓMETROS y el rendimiento)
 * Litros = km / (km/L)
 * Emisiones = (km / rendimientoKmPorL) * factor (kg CO2e/L)
 */
fun gasolineEmissionsFromKmKgCO2e(
    km: Double,
    efficiencyKmPorL: Double = EmissionFactors.GASOLINE_EFFICIENCY_KM_PER_LITER,
    factorKgPerLiter: Double = EmissionFactors.GASOLINE_KG_PER_LITER
): Double = (km / efficiencyKmPorL) * factorKgPerLiter

/**
 * GAS LP
 * Emisiones = kg de gas LP * factor (kg CO2e/kg)
 */
fun lpgEmissionsKgCO2e(
    kgLpg: Double,
    factorKgPerKg: Double = EmissionFactors.LPG_KG_PER_KG
): Double = kgLpg * factorKgPerKg

fun Double.monthlyToAnnual(): Double = this * 12.0
