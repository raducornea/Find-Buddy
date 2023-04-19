package com.project.profile.business.services

import org.springframework.stereotype.Service

@Service
class SimilarWordsService {

    fun getClosestLiteralMatches(input: String, options: List<String>): List<String> {
        // the user should see a maximum of 10 elements, but it could go to 0 too
        val limit = minOf(options.size, 10)

        // should see user hashtags in common too...
        val closestMatches = options.map { it to levenshteinDistance(input, it) }
            .sortedBy { (_, distance) -> distance }
            .take(limit)
            .map { (option, _) -> option }

        return closestMatches
    }

    fun levenshteinDistance(string1: String, string2: String): Int {
        val m = string1.length
        val n = string2.length
        val dist = Array(m + 1) { IntArray(n + 1) }

        for (i in 0..m) {
            dist[i][0] = i
        }
        for (j in 0..n) {
            dist[0][j] = j
        }

        for (j in 1..n) {
            for (i in 1..m) {
                val substitutionCost = if (string1[i - 1] == string2[j - 1]) 0 else 1
                dist[i][j] = minOf(
                    dist[i - 1][j] + 1,                    // deletion
                    dist[i][j - 1] + 1,                    // insertion
                    dist[i - 1][j - 1] + substitutionCost  // substitution
                )
            }
        }

        return dist[m][n]
    }
}