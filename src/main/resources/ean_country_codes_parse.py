# https://sv.wikipedia.org/wiki/Lista_%C3%B6ver_GS1_landskoder
import re

dump = """

"""

matches = re.findall(r'(((\d{3})\–(\d{3}))|\d{3})', dump)

output = [int(i[0]) for i in matches if "–" not in i[0]] + [item for sublist in [list(range(int(j.split("-")[0]), int(j.split("-")[1])+1)) for j in [i[0].replace("–", "-") for i in matches if "–" in i[0]]] for item in sublist]

output.sort()

# remove 200-299, internal usage
output = [i for i in output if i < 200 or i > 299]