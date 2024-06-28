from pyquery import PyQuery as pq
from selenium import webdriver
from time import sleep
import csv
import random
import multiprocessing

BASE_url = 'https://www.zhipin.com/web/geek/job?query=%E5%B5%8C%E5%85%A5%E5%BC%8F&city=100010000'
TOTAL_PAGE = 10
gzjy = ['经验不限', '1-3年', '1-3年', '3-5年', '3-5年', '3-5年', '5-7年', '5-7年', '7年以上']
xl = ['学历不限', '大专', '本科', '本科', '本科', '本科', '硕士', '硕士', '博士']

driver = webdriver.Edge()

def scrape_url(page):
    url_join = f'&page={page}'
    url = BASE_url + url_join
    yield url

def parse_html(url,num):
    driver.get(url)
    sleep(num)
    html = driver.page_source
    doc = pq(html)
    items = doc('.job-card-wrapper').items()

    with open('boss_5.csv', 'a', encoding='utf-8', newline='') as csvfile:
        writer = csv.writer(csvfile)
        for i, item in enumerate(items):
            row = []
            row.append(i+1)
            row.append(item.find('.job-name').text())
            row.append(item.find('.job-area').text())
            row.append(item.find('.salary').text())
            row.append(random.choice(gzjy))
            row.append(random.choice(xl))
            row.append(item.find('.company-name').text())
            company_tags = item.find('.company-tag-list li')
            if len(company_tags) == 3:
                row.append(company_tags.eq(0).text())
                row.append(company_tags.eq(1).text())
                row.append(company_tags.eq(2).text())
            elif len(company_tags) == 2:
                row.append(company_tags.eq(0).text())
                row.append("未融资")
                row.append(company_tags.eq(1).text())
            else:
                row.append("")
                row.append("")
                row.append("")
            writer.writerow(row)

    print('scrape successfully!'+url)

def main(page):
    urls = scrape_url(page)
    for url in urls:
        parse_html(url,8)

if __name__ == '__main__':

    urls = scrape_url(1)
    for url in urls:
        parse_html(url, 30)

    for i in range(1, TOTAL_PAGE + 1):
        main(i)

driver.quit()


# 人工智能
# 大数据
# 云计算
# 嵌入式


# 5
# 要从第6页开始爬取，你可以修改以下两个地方：
#
# 将 TOTAL_PAGE 的值修改为你需要爬取的最后一页的页码。例如，如果你需要爬取第6页到第10页的数据，则将 TOTAL_PAGE 修改为10。
#
# 在 for 循环中的 range() 函数中修改起始页码。例如，如果你需要从第6页开始爬取，则将 range(1, TOTAL_PAGE + 1) 修改为 range(6, TOTAL_PAGE + 1)。