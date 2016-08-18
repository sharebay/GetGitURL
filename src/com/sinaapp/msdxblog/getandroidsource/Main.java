/*
 * @(#)Main.java                       Project:GetAndroidSource
 * Date:2013-9-1
 *
 * Copyright (c) 2013 CFuture09, Institute of Software, 
 * Guangdong Ocean University, Zhanjiang, GuangDong, China.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sinaapp.msdxblog.getandroidsource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Geek_Soledad <a target="_blank" href=
 *         "http://mail.qq.com/cgi-bin/qm_share?t=qm_mailme&email=XTAuOSVzPDM5LzI0OR0sLHM_MjA"
 *         style="text-decoration:none;"><img src=
 *         "http://rescdn.qqmail.com/zh_CN/htmledition/images/function/qm_open/ico_mailme_01.png"
 *         /></a>
 */
public class Main {

        private static final String GITHUB_URL = "https://github.com";
        private static final String GITHUB_ANDROID_URL = "https://github.com/android";

        public static void main(String[] args) {
                List<GithubUrl> urls = getGithubUrl();
                System.out.println("repolists: " + urls.size());
                try {
                        FileUtils.writeLines(new File("D:\\gitclone.txt"), urls);
                        System.out.println("write to D:\\gitclone.txt finish");
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        private static List<GithubUrl> getGithubUrl() {
                Document doc = null;
                try {
                        doc = Jsoup.connect(GITHUB_ANDROID_URL).get();
                } catch (IOException e) {
                        e.printStackTrace();
                }
                if (doc == null) {
                        return null;
                }
                List<GithubUrl> urls = new ArrayList<GithubUrl>();
                Elements repolists = doc.getElementsByClass("repo-list-name");
                //<h3 class="repo-list-name">
                //<a href="/android/platform_build" itemprop="name codeRepository">
                //platform_build</a>
                //</h3>
                for (Element repolist : repolists) {
                        Elements hrefs = repolist.select("a[href]");
                        if (hrefs.isEmpty()) {
                                System.out.println("it is empth");
                                continue;
                        }
                        if (hrefs.size() > 1) {
                                System.out.println(hrefs.text() + ":" + hrefs.size());
                        }
                        Element e = hrefs.first();
                        System.out.println(e.html() + ":" + e.text() + "---" + e.attr("href"));
                        urls.add(new GithubUrl(e.text().replaceAll("_", "/"), GITHUB_URL + e.attr("href")));
                }
                return urls;
        }

}