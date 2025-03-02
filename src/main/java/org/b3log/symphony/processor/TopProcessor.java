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
package org.b3log.symphony.processor;

import org.b3log.latke.http.Dispatcher;
import org.b3log.latke.http.RequestContext;
import org.b3log.latke.http.renderer.AbstractFreeMarkerRenderer;
import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.ioc.Singleton;
import org.b3log.symphony.model.Common;
import org.b3log.symphony.processor.middleware.AnonymousViewCheckMidware;
import org.b3log.symphony.service.*;
import org.b3log.symphony.util.Symphonys;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Top ranking list processor.
 * <ul>
 * <li>Shows top (/top), GET</li>
 * <li>Top balance ranking list (/top/balance), GET</li>
 * <li>Top consumption ranking list (/top/consumption), GET</li>
 * <li>Top checkin ranking list (/top/checkin), GET</li>
 * <li>Top link ranking list (/top/link), GET</li>
 * </ul>
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 2.0.0.0, Feb 11, 2020
 * @since 1.3.0
 */
@Singleton
public class TopProcessor {

    /**
     * Data model service.
     */
    @Inject
    private DataModelService dataModelService;

    /**
     * Pointtransfer query service.
     */
    @Inject
    private PointtransferQueryService pointtransferQueryService;

    /**
     * Activity query service.
     */
    @Inject
    private ActivityQueryService activityQueryService;

    /**
     * User query service.
     */
    @Inject
    private UserQueryService userQueryService;

    /**
     * Link query service.
     */
    @Inject
    private LinkQueryService linkQueryService;

    /**
     * Register request handlers.
     */
    public static void register() {
        final BeanManager beanManager = BeanManager.getInstance();
        final AnonymousViewCheckMidware anonymousViewCheckMidware = beanManager.getReference(AnonymousViewCheckMidware.class);

        final TopProcessor topProcessor = beanManager.getReference(TopProcessor.class);
        Dispatcher.get("/top", topProcessor::showTop, anonymousViewCheckMidware::handle);
        Dispatcher.get("/top/link", topProcessor::showLink, anonymousViewCheckMidware::handle);
        Dispatcher.get("/top/balance", topProcessor::showBalance, anonymousViewCheckMidware::handle);
        Dispatcher.get("/top/consumption", topProcessor::showConsumption, anonymousViewCheckMidware::handle);
        Dispatcher.get("/top/checkin", topProcessor::showCheckin, anonymousViewCheckMidware::handle);
        Dispatcher.get("/top/online", topProcessor::showOnline, anonymousViewCheckMidware::handle);
        Dispatcher.get("/top/adr", topProcessor::showADR, anonymousViewCheckMidware::handle);
        Dispatcher.get("/top/mofish", topProcessor::showMofish, anonymousViewCheckMidware::handle);
        Dispatcher.get("/top/lifeRestart", topProcessor::showLifeRestart, anonymousViewCheckMidware::handle);
        Dispatcher.get("/top/evolve", topProcessor::showEvolve, anonymousViewCheckMidware::handle);
    }

    /**
     * Shows top.
     *
     * @param context the specified context
     */
    public void showTop(final RequestContext context) {
        final AbstractFreeMarkerRenderer renderer = new SkinRenderer(context, "top/index.ftl");
        final Map<String, Object> dataModel = renderer.getDataModel();
        dataModel.put(Common.SELECTED, Common.TOP);

        dataModelService.fillHeaderAndFooter(context, dataModel);
        dataModelService.fillRandomArticles(dataModel);
        dataModelService.fillSideHotArticles(dataModel);
        dataModelService.fillSideTags(dataModel);
        dataModelService.fillLatestCmts(dataModel);
    }

    /**
     * Shows link ranking list.
     *
     * @param context the specified context
     */
    public void showLink(final RequestContext context) {
        final AbstractFreeMarkerRenderer renderer = new SkinRenderer(context, "top/link.ftl");
        final Map<String, Object> dataModel = renderer.getDataModel();
        final List<JSONObject> topLinks = linkQueryService.getTopLink(Symphonys.TOP_CNT);
        dataModel.put(Common.TOP_LINKS, topLinks);

        dataModelService.fillHeaderAndFooter(context, dataModel);
        dataModelService.fillRandomArticles(dataModel);
        dataModelService.fillSideHotArticles(dataModel);
        dataModelService.fillSideTags(dataModel);
        dataModelService.fillLatestCmts(dataModel);
    }

    /**
     * Shows balance ranking list.
     *
     * @param context the specified context
     */
    public void showBalance(final RequestContext context) {
        final AbstractFreeMarkerRenderer renderer = new SkinRenderer(context, "top/balance.ftl");
        final Map<String, Object> dataModel = renderer.getDataModel();
        final List<JSONObject> users = pointtransferQueryService.getTopBalanceUsers(Symphonys.TOP_CNT);
        dataModel.put(Common.TOP_BALANCE_USERS, users);

        dataModelService.fillHeaderAndFooter(context, dataModel);
        dataModelService.fillRandomArticles(dataModel);
        dataModelService.fillSideHotArticles(dataModel);
        dataModelService.fillSideTags(dataModel);
        dataModelService.fillLatestCmts(dataModel);
    }

    /**
     * Shows consumption ranking list.
     *
     * @param context the specified context
     */
    public void showConsumption(final RequestContext context) {
        final AbstractFreeMarkerRenderer renderer = new SkinRenderer(context, "top/consumption.ftl");
        final Map<String, Object> dataModel = renderer.getDataModel();
        final List<JSONObject> users = pointtransferQueryService.getTopConsumptionUsers(Symphonys.TOP_CNT);
        dataModel.put(Common.TOP_CONSUMPTION_USERS, users);

        dataModelService.fillHeaderAndFooter(context, dataModel);
        dataModelService.fillRandomArticles(dataModel);
        dataModelService.fillSideHotArticles(dataModel);
        dataModelService.fillSideTags(dataModel);
        dataModelService.fillLatestCmts(dataModel);
    }

    /**
     * Shows checkin ranking list.
     *
     * @param context the specified context
     */
    public void showCheckin(final RequestContext context) {
        final AbstractFreeMarkerRenderer renderer = new SkinRenderer(context, "top/checkin.ftl");
        final Map<String, Object> dataModel = renderer.getDataModel();
        final List<JSONObject> users = activityQueryService.getTopCheckinUsers(Symphonys.TOP_CNT);
        dataModel.put(Common.TOP_CHECKIN_USERS, users);

        dataModelService.fillHeaderAndFooter(context, dataModel);
        dataModelService.fillRandomArticles(dataModel);
        dataModelService.fillSideHotArticles(dataModel);
        dataModelService.fillSideTags(dataModel);
        dataModelService.fillLatestCmts(dataModel);
    }

    /**
     * Shows online ranking list.
     *
     * @param context the specified context
     */
    public void showOnline(final RequestContext context) {
        final AbstractFreeMarkerRenderer renderer = new SkinRenderer(context, "top/online.ftl");
        final Map<String, Object> dataModel = renderer.getDataModel();
        final List<JSONObject> users = activityQueryService.getTopOnlineTimeUsers(Symphonys.TOP_CNT);
        dataModel.put("onlineTopUsers", users);

        dataModelService.fillHeaderAndFooter(context, dataModel);
        dataModelService.fillRandomArticles(dataModel);
        dataModelService.fillSideHotArticles(dataModel);
        dataModelService.fillSideTags(dataModel);
        dataModelService.fillLatestCmts(dataModel);
    }

    /**
     * Shows ADR score ranking list.
     *
     * @param context
     */
    public void showADR(final RequestContext context) {
        final AbstractFreeMarkerRenderer renderer = new SkinRenderer(context, "top/adr.ftl");
        final Map<String, Object> dataModel = renderer.getDataModel();
        final List<JSONObject> users = activityQueryService.getTopADR(Symphonys.TOP_CNT);
        dataModel.put("topUsers", users);

        dataModelService.fillHeaderAndFooter(context, dataModel);
        dataModelService.fillRandomArticles(dataModel);
        dataModelService.fillSideHotArticles(dataModel);
        dataModelService.fillSideTags(dataModel);
        dataModelService.fillLatestCmts(dataModel);
    }

    /**
     * Shows Mofish score ranking list.
     *
     * @param context
     */
    public void showMofish(final RequestContext context) {
        final AbstractFreeMarkerRenderer renderer = new SkinRenderer(context, "top/mofish.ftl");
        final Map<String, Object> dataModel = renderer.getDataModel();
        final List<JSONObject> users = activityQueryService.getTopMofish(Symphonys.TOP_CNT);
        dataModel.put("topUsers", users);

        dataModelService.fillHeaderAndFooter(context, dataModel);
        dataModelService.fillRandomArticles(dataModel);
        dataModelService.fillSideHotArticles(dataModel);
        dataModelService.fillSideTags(dataModel);
        dataModelService.fillLatestCmts(dataModel);
    }

    /**
     * Shows Life Restart ranking list.
     *
     * @param context
     */
    public void showLifeRestart(final RequestContext context) {
        final AbstractFreeMarkerRenderer renderer = new SkinRenderer(context, "top/lifeRestart.ftl");
        final Map<String, Object> dataModel = renderer.getDataModel();
        final List<JSONObject> users = activityQueryService.getTopLifeRestart(Symphonys.TOP_CNT);
        dataModel.put("topUsers", users);

        dataModelService.fillHeaderAndFooter(context, dataModel);
        dataModelService.fillRandomArticles(dataModel);
        dataModelService.fillSideHotArticles(dataModel);
        dataModelService.fillSideTags(dataModel);
        dataModelService.fillLatestCmts(dataModel);
    }

    /**
     * Shows Evolve ranking list.
     *
     * @param context
     */
    public void showEvolve(final RequestContext context) {
        final AbstractFreeMarkerRenderer renderer = new SkinRenderer(context, "top/evolve.ftl");
        final Map<String, Object> dataModel = renderer.getDataModel();
        final List<JSONObject> users = activityQueryService.getEvolve(Symphonys.TOP_CNT);
        dataModel.put("topUsers", users);

        dataModelService.fillHeaderAndFooter(context, dataModel);
        dataModelService.fillRandomArticles(dataModel);
        dataModelService.fillSideHotArticles(dataModel);
        dataModelService.fillSideTags(dataModel);
        dataModelService.fillLatestCmts(dataModel);
    }
}
