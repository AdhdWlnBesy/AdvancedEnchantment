# Advanced Enchantments

Advanced Enchantments 是一个面向 Minecraft **1.21.1** 的 Fabric 模组，
为游戏加入新的附魔体系、附魔书/卷轴、高级铁砧、高级砂轮、高级锻造台，
以及五座可探索的地牢。模组以数据驱动为主，附魔、结构、战利品和语言文件都可以在源码中继续扩展。
拓展附魔可以实现接口进行模块化

## 主要内容

- **四档附魔**：普通（Ordinary）、高级（Advanced）、稀有（Rare）、珍贵（Precious），覆盖武器、工具、护甲、弓箭、钓鱼竿等类型。
- **附魔载体**：附魔书、附魔卷轴、升级球、附魔槽位建造器，以及一本可翻阅的附魔指南。
- **高级工作台**：高级铁砧、高级砂轮、高级锻造台，支持自定义的附魔转移、去除与锻造逻辑。
- **地牢**：五座不同主题的地牢，包含主控方块、刷怪笼、宝库、钥匙和对应的战利品池；结构会随世界生成并支持旋转。
- **数据生成**：使用 Fabric Data Generation 生成进度、附魔、模型、配方、标签、战利品表和中英文语言文件。
- 详情参阅MC百科

## 环境要求

| 项目 | 版本 |
| --- | --- |
| Minecraft | 1.21.1 |
| Fabric Loader | 0.18.4 或更高 |
| Fabric API | 0.116.9+1.21.1 |
| Java | 21 |

## 构建

项目使用 Gradle Wrapper，首次构建会自动下载依赖：
```bash
./gradlew build
```
```powershell
.\gradlew.bat build
```
构建产物位于 `build/libs/`。

## 项目结构

```text
src/main/java/com/advanced_enchants/
├── block/          方块与方块实体（地牢主控、刷怪笼、宝库等）
├── datagen/        数据生成器
├── enchantment/    附魔实现与效果处理
├── entity/         地牢生物与怪物守卫
├── event/          交互、网络、进度等事件
├── gui/            高级工作台与指南界面
├── mixin/          对原版行为的 Mixin 修改
└── register/       注册入口与公共工具
src/main/resources/
├── assets/         模型、纹理、语言文件
├── data/           附魔、结构、战利品表、标签与配方
└── fabric.mod.json
```

## 开发说明

- 可通过在 `ModEnchantments.registerEnchantments` 里注册RegistryKey ， 注册json文件， 继承 `advanced_enchants.enchantment.effect`
的接口以及加入`EnchantmentHandler`的列表实现附魔效果，来拓展一个高级附魔，相应的这个附魔会加入法术转换列表，也会显示在高级附魔图鉴中。
- 修改注册内容后，可通过数据生成任务更新 `src/main/generated` 下的文件。
- 本项目使用 MIT 许可，可自由使用、修改和分发，但是要标注来源。

## 许可

本项目采用 [MIT](LICENSE) 许可。
