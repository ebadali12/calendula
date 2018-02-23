/*
 *    Calendula - An assistant for personal medication management.
 *    Copyright (C) 2014-2018 CiTIUS - University of Santiago de Compostela
 *
 *    Calendula is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this software.  If not, see <http://www.gnu.org/licenses/>.
 */

package es.usc.citius.servando.calendula.persistence

import com.google.ical.values.Frequency
import org.joda.time.DateTime
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class RepetitionRuleTest(
    intervalIn: Int,
    frequencyIn: Frequency,
    fromString: String,
    toString: String,
    scheduleStartString: String,
    val expectedOccurrences: Int
) {

    companion object {
        @Parameterized.Parameters
        @JvmStatic
        fun getParameters() = listOf(
            arrayOf(8, Frequency.HOURLY, "2018-01-01", "2018-01-02", "2018-01-01", 3),
            arrayOf(3, Frequency.HOURLY, "2018-01-01", "2018-01-02", "2018-01-01", 8),
            arrayOf(48, Frequency.HOURLY, "2018-01-01", "2018-01-04", "2018-01-01", 2),
            arrayOf(1, Frequency.HOURLY, "2018-01-01", "2018-01-04", "2018-01-01", 72),
            arrayOf(1, Frequency.HOURLY, "2018-01-01", "2018-01-04", "2018-01-05", 0),
            arrayOf(8, Frequency.HOURLY, "2018-01-03", "2018-01-04", "2018-01-01", 3),
            arrayOf(3, Frequency.HOURLY, "2018-01-03", "2018-01-04", "2018-01-01", 8),
            arrayOf(48, Frequency.HOURLY, "2018-01-03", "2018-01-06", "2018-01-01", 2),
            arrayOf(1, Frequency.HOURLY, "2018-01-03", "2018-01-06", "2018-01-01", 72)
            )
    }

    val from: DateTime = DateTime.parse(fromString)
    val to: DateTime = DateTime.parse(toString)
    val scheduleStart = DateTime.parse(scheduleStartString)
    val rule = RepetitionRule().apply {
        frequency = frequencyIn
        interval = intervalIn
    }


    @Test
    fun testOccurrencesBetween() {

        println("Testing from $from to $to, with schedule starting at $scheduleStart, and repetition rule with interval ${rule.interval} ${rule.frequency}")

        val occurrences = rule.occurrencesBetween(from, to, scheduleStart).size

        Assert.assertEquals("Wrong number of occurrences", expectedOccurrences, occurrences)
    }

}