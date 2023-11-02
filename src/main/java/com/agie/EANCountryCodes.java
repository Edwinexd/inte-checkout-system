/*
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
*/
package com.agie;

public class EANCountryCodes {
    // Generated via resources/valid_country_codes.py
    public static final int[] VALID_COUNTRY_CODES = { 380, 383, 385, 387, 389, 390, 440, 199, 470, 471, 474, 475, 476,
            477, 478, 479, 480, 481, 482, 483, 484, 485, 486, 487, 488, 489, 528, 529, 530, 531, 535, 539, 560, 569,
            590, 594, 599, 603, 604, 608, 609, 611, 613, 615, 616, 618, 619, 620, 621, 622, 623, 624, 625, 626, 627,
            628, 629, 729, 740, 741, 742, 743, 744, 745, 746, 750, 759, 773, 775, 777, 780, 784, 786, 850, 858, 859,
            860, 865, 867, 880, 884, 885, 888, 890, 893, 197, 896, 899, 950, 951, 955, 958, 977, 979, 980, 100, 101,
            102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122,
            123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 200, 201, 202, 203,
            204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224,
            225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245,
            246, 247, 248, 249, 250, 251, 252, 253, 254, 255, 256, 257, 258, 259, 260, 261, 262, 263, 264, 265, 266,
            267, 268, 269, 270, 271, 272, 273, 274, 275, 276, 277, 278, 279, 280, 281, 282, 283, 284, 285, 286, 287,
            288, 289, 290, 291, 292, 293, 294, 295, 296, 297, 298, 299, 300, 301, 302, 303, 304, 305, 306, 307, 308,
            309, 310, 311, 312, 313, 314, 315, 316, 317, 318, 319, 320, 321, 322, 323, 324, 325, 326, 327, 328, 329,
            330, 331, 332, 333, 334, 335, 336, 337, 338, 339, 340, 341, 342, 343, 344, 345, 346, 347, 348, 349, 350,
            351, 352, 353, 354, 355, 356, 357, 358, 359, 360, 361, 362, 363, 364, 365, 366, 367, 368, 369, 370, 371,
            372, 373, 374, 375, 376, 377, 378, 379, 400, 401, 402, 403, 404, 405, 406, 407, 408, 409, 410, 411, 412,
            413, 414, 415, 416, 417, 418, 419, 420, 421, 422, 423, 424, 425, 426, 427, 428, 429, 430, 431, 432, 433,
            434, 435, 436, 437, 438, 439, 440, 450, 451, 452, 453, 454, 455, 456, 457, 458, 459, 460, 461, 462, 463,
            464, 465, 466, 467, 468, 469, 490, 491, 492, 493, 494, 495, 496, 497, 498, 499, 500, 501, 502, 503, 504,
            505, 506, 507, 508, 509, 520, 521, 540, 541, 542, 543, 544, 545, 546, 547, 548, 549, 570, 571, 572, 573,
            574, 575, 576, 577, 578, 579, 600, 601, 640, 641, 642, 643, 644, 645, 646, 647, 648, 649, 690, 691, 692,
            693, 694, 695, 696, 697, 698, 699, 700, 701, 702, 703, 704, 705, 706, 707, 708, 709, 730, 731, 732, 733,
            734, 735, 736, 737, 738, 739, 754, 755, 760, 761, 762, 763, 764, 765, 766, 767, 768, 769, 770, 771, 778,
            779, 789, 790, 800, 801, 802, 803, 804, 805, 806, 807, 808, 809, 810, 811, 812, 813, 814, 815, 816, 817,
            818, 819, 820, 821, 822, 823, 824, 825, 826, 827, 828, 829, 830, 831, 832, 833, 834, 835, 836, 837, 838,
            839, 840, 841, 842, 843, 844, 845, 846, 847, 848, 849, 868, 869, 870, 871, 872, 873, 874, 875, 876, 877,
            878, 879, 900, 901, 902, 903, 904, 905, 906, 907, 908, 909, 910, 911, 912, 913, 914, 915, 916, 917, 918,
            919, 930, 931, 932, 933, 934, 935, 936, 937, 938, 939, 940, 941, 942, 943, 944, 945, 946, 947, 948, 949,
            960, 961, 962, 963, 964, 965, 966, 967, 968, 969, 978, 979, 981, 982, 983, 984, 990, 991, 992, 993, 994,
            995, 996, 997, 998, 999 };

    public static final int INTERNAL_COUNTRY_CODE_RANGE_START = 200;
    public static final int INTERNAL_COUNTRY_CODE_RANGE_END = 299;

    public static boolean isValidCountryCode(int countryCode) {
        for (int validCountryCode : VALID_COUNTRY_CODES) {
            if (countryCode == validCountryCode) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInternalCountryCode(int countryCode) {
        return countryCode >= INTERNAL_COUNTRY_CODE_RANGE_START && countryCode <= INTERNAL_COUNTRY_CODE_RANGE_END;
    }

}
