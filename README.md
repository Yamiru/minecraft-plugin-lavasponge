<div align="center">

# 🧽 LavaSponge

**Simple and optimized Minecraft plugin to absorb lava with sponges**

[![Release](https://img.shields.io/github/v/release/Yamiru/minecraft-plugin-lavasponge?style=for-the-badge&logo=github)](https://github.com/Yamiru/minecraft-plugin-lavasponge/releases/latest)
[![Downloads](https://img.shields.io/github/downloads/Yamiru/minecraft-plugin-lavasponge/total?style=for-the-badge&logo=github)](https://github.com/Yamiru/minecraft-plugin-lavasponge/releases)
[![License](https://img.shields.io/github/license/Yamiru/minecraft-plugin-lavasponge?style=for-the-badge)](LICENSE)
[![Build](https://img.shields.io/github/actions/workflow/status/Yamiru/minecraft-plugin-lavasponge/build.yml?style=for-the-badge&logo=github-actions)](https://github.com/Yamiru/minecraft-plugin-lavasponge/actions)

[Features](#-features) • [Installation](#-installation) • [Configuration](#%EF%B8%8F-configuration) • [Commands](#-commands)

</div>

---

## ✨ Features

- 🧽 **Absorb lava with sponges** - Right-click near lava with a sponge to absorb it
- 🌍 **World-based control** - Enable/disable in specific worlds
- 🔑 **Permission system** - Full control via `lavasponge.use` permission
- ⚙️ **Configurable radius** - Adjust absorption range
- 💧 **Wet sponge option** - Choose if sponges become wet or disappear
- 🚀 **Optimized performance** - Efficient code with minimal lag
- 🔄 **Hot reload** - Reload configuration without restarting

## 📥 Installation

1. **Download** the latest `LavaSponge-X.X.X.jar` from [Releases](https://github.com/Yamiru/minecraft-plugin-lavasponge/releases/latest)
2. **Place** the JAR file in your server's `plugins/` folder
3. **Restart** your server
4. **Configure** permissions and settings (see below)
5. **Enjoy!** 🎉

## 🔑 Permissions

| Permission | Description | Default |
|------------|-------------|---------|
| `lavasponge.use` | Allows using sponge to absorb lava | `false` |
| `lavasponge.admin` | Allows using `/lavasponge` commands | `op` |

### Setting Up Permissions

**By default, nobody can use the plugin!** You must explicitly grant permissions.

<details>
<summary><b>LuckPerms</b></summary>

```bash
# Grant to a player
/lp user PlayerName permission set lavasponge.use true

# Grant to a group
/lp group vip permission set lavasponge.use true

# Grant to everyone (default group)
/lp group default permission set lavasponge.use true
```
</details>


## 📝 Commands

| Command | Aliases | Description | Permission |
|---------|---------|-------------|------------|
| `/lavasponge info` | `/ls info`, `/lsponge info` | Show plugin information | `lavasponge.admin` |
| `/lavasponge reload` | `/ls reload`, `/lsponge reload` | Reload configuration | `lavasponge.admin` |

## ⚙️ Configuration

The configuration file is located at `plugins/LavaSponge/config.yml`:

```yaml
# Enable or disable the plugin globally
enabled: true

# Worlds where lava sponge is enabled (empty = all worlds)
enabled-worlds: []

# Disabled worlds (priority over enabled-worlds)
disabled-worlds: []

# Lava absorption settings
absorption:
  radius: 2           # Absorption radius around clicked block
  wet-sponge: false   # Convert to wet sponge (false = sponge disappears)
  max-blocks: 65      # Maximum blocks to remove per use
```

### Configuration Examples

<details>
<summary><b>Enable only in specific worlds</b></summary>

```yaml
enabled-worlds: 
  - world
  - world_nether
```
</details>

<details>
<summary><b>Disable in creative world</b></summary>

```yaml
disabled-worlds:
  - creative
```
</details>

<details>
<summary><b>Convert sponges to wet sponges</b></summary>

```yaml
absorption:
  wet-sponge: true
```
</details>


## 📋 Requirements

- **Minecraft Server**: Spigot, Paper, or Purpur 1.20+
- **Java**: 17 or higher
- **Permissions Plugin**: LuckPerms, etc. (optional but recommended)

## 🎯 Performance Optimizations

This plugin is designed with performance in mind:

- ✅ **HashSet for O(1) lookups** instead of List O(n) searches
- ✅ **EventPriority.HIGH** for better event handling
- ✅ **Lazy initialization** for minimal memory footprint
- ✅ **Efficient permission checks** using native Bukkit system
- ✅ **Minimal CPU operations** with early-return patterns


## 🐛 Bug Reports & Feature Requests

Found a bug or have a feature request? Please open an [issue](https://github.com/Yamiru/minecraft-plugin-lavasponge/issues/new/choose).

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Yamiru**
- GitHub: [@Yamiru](https://github.com/Yamiru)
- Repository: [minecraft-plugin-lavasponge](https://github.com/Yamiru/minecraft-plugin-lavasponge)

---

<div align="center">

**If you find this plugin useful, please give it a ⭐ on GitHub!**

Made with ❤️ by [Yamiru](https://yamiru.com)

</div>
