"""
Checkout System - A checkout system with focus on testing - group assignment for the INTE course at Stockholm University Autumn 2023
Copyright (C) 2023 Gusten Berghäll, Ida Laaksonen, Adrian Martvall, Edwin Sundberg 

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
"""
# https://sv.wikipedia.org/wiki/Lista_%C3%B6ver_GS1_landskoder
import re

dump = """

"""

matches = re.findall(r'(((\d{3})\–(\d{3}))|\d{3})', dump)

output = [int(i[0]) for i in matches if "–" not in i[0]] + [item for sublist in [list(range(int(j.split("-")[0]), int(j.split("-")[1])+1)) for j in [i[0].replace("–", "-") for i in matches if "–" in i[0]]] for item in sublist]

output.sort()

# remove 200-299, internal usage
output = [i for i in output if i < 200 or i > 299]