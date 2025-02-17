/*
 * Rhythm - A modern community (forum/BBS/SNS/blog) platform written in Java.
 * Modified version from Symphony, Thanks Symphony :)
 * Copyright (C) 2012-present, b3log.org
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package org.b3log.symphony.util;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Gravatar (http://www.gravatar.com) utilities.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.0, Feb 12, 2018
 * @since 2.5.0
 */
public final class Gravatars {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(Gravatars.class);

    /**
     * Styles.
     */
    private static final String[] d = new String[]{"identicon", "monsterid", "wavatar", "retro", "robohash"};

    /**
     * Gets random avatar image byte array data with the specified hash.
     *
     * @param hash the specified hash
     * @return avatar image byte array date, returns {@code null} if failed to get avatar
     */
    public static byte[] getRandomAvatarData(final String hash) {
        try {
            String h = hash;
            if (StringUtils.isBlank(h)) {
                h = RandomStringUtils.randomAlphanumeric(16);
            }

            final HttpResponse response = HttpRequest.get("http://www.gravatar.com/avatar/" + h + "?s=256&d=" + d[RandomUtils.nextInt(d.length)]).
                    connectionTimeout(5000).timeout(5000).send();
            if (200 != response.statusCode()) {
                LOGGER.log(Level.WARN, "Gets avatar data failed [sc=" + response.statusCode() + "]");

                return null;
            }

            return response.bodyBytes();
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Gets avatar data failed", e);

            return null;
        }
    }

    /**
     * Private constructor.
     */
    private Gravatars() {
    }
}
