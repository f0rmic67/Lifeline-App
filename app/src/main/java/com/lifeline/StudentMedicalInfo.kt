package com.lifeline

import java.math.BigInteger

/**
 * {'id': 1,
 * 'student_id': 1,
 * 'dob': datetime.date(2024, 4, 3),
 * 'heart_problems': 0, 'heart_problems_medications': '',
 * 'pacemaker': 0, 'pacemaker_medications': '',
 * 'diabetes': 1, 'diabetes_medications': 'Type 1',
 * 'high_bp': 0, 'high_bp_medications': '',
 * 'stroke': 0, 'stroke_medications': '',
 * 'asthma_copd': 1, 'asthma_copd_medications': 'Asthma, rescue inhaler',
 * 'seizures': 0, 'seizures_medications': '',
 * 'cancer': 0, 'cancer_medications': '',
 * 'allergies': 1, 'allergies_medications': 'bees, dogs',
 * 'other': 0, 'other_medications': '', 'last_update': None}
 */
data class StudentMedicalInfo(
    val id: BigInteger,
    val student_id: BigInteger,
    val dob:String,
    val heart_problems:Int, val heart_problems_medications:String,
    val pacemaker:Int, val pacemaker_medications:String,
    val diabetes:Int, val diabetes_medications:String,
    val high_bp:Int, val high_bp_medications:String,
    val stroke:Int, val stroke_medications:String,
    val asthma_copd:Int, val asthma_copd_medications:String,
    val seizures:Int, val seizures_medications:String,
    val cancer:Int, val cancer_medications:String,
    val allergies:Int, val allergies_medications:String,
    val other:Int, val other_medications:String,
)