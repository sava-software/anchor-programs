package software.sava.anchor.programs.kamino.farms.anchor.types;

import java.math.BigInteger;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record GlobalConfig(PublicKey _address,
                           Discriminator discriminator,
                           PublicKey globalAdmin,
                           long treasuryFeeBps,
                           PublicKey treasuryVaultsAuthority,
                           long treasuryVaultsAuthorityBump,
                           PublicKey pendingGlobalAdmin,
                           BigInteger[] padding1) implements Borsh {

  public static final int BYTES = 2136;
  public static final int PADDING_1_LEN = 126;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int GLOBAL_ADMIN_OFFSET = 8;
  public static final int TREASURY_FEE_BPS_OFFSET = 40;
  public static final int TREASURY_VAULTS_AUTHORITY_OFFSET = 48;
  public static final int TREASURY_VAULTS_AUTHORITY_BUMP_OFFSET = 80;
  public static final int PENDING_GLOBAL_ADMIN_OFFSET = 88;
  public static final int PADDING_1_OFFSET = 120;

  public static Filter createGlobalAdminFilter(final PublicKey globalAdmin) {
    return Filter.createMemCompFilter(GLOBAL_ADMIN_OFFSET, globalAdmin);
  }

  public static Filter createTreasuryFeeBpsFilter(final long treasuryFeeBps) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, treasuryFeeBps);
    return Filter.createMemCompFilter(TREASURY_FEE_BPS_OFFSET, _data);
  }

  public static Filter createTreasuryVaultsAuthorityFilter(final PublicKey treasuryVaultsAuthority) {
    return Filter.createMemCompFilter(TREASURY_VAULTS_AUTHORITY_OFFSET, treasuryVaultsAuthority);
  }

  public static Filter createTreasuryVaultsAuthorityBumpFilter(final long treasuryVaultsAuthorityBump) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, treasuryVaultsAuthorityBump);
    return Filter.createMemCompFilter(TREASURY_VAULTS_AUTHORITY_BUMP_OFFSET, _data);
  }

  public static Filter createPendingGlobalAdminFilter(final PublicKey pendingGlobalAdmin) {
    return Filter.createMemCompFilter(PENDING_GLOBAL_ADMIN_OFFSET, pendingGlobalAdmin);
  }

  public static GlobalConfig read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static GlobalConfig read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static GlobalConfig read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], GlobalConfig> FACTORY = GlobalConfig::read;

  public static GlobalConfig read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var globalAdmin = readPubKey(_data, i);
    i += 32;
    final var treasuryFeeBps = getInt64LE(_data, i);
    i += 8;
    final var treasuryVaultsAuthority = readPubKey(_data, i);
    i += 32;
    final var treasuryVaultsAuthorityBump = getInt64LE(_data, i);
    i += 8;
    final var pendingGlobalAdmin = readPubKey(_data, i);
    i += 32;
    final var padding1 = new BigInteger[126];
    Borsh.read128Array(padding1, _data, i);
    return new GlobalConfig(_address,
                            discriminator,
                            globalAdmin,
                            treasuryFeeBps,
                            treasuryVaultsAuthority,
                            treasuryVaultsAuthorityBump,
                            pendingGlobalAdmin,
                            padding1);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    globalAdmin.write(_data, i);
    i += 32;
    putInt64LE(_data, i, treasuryFeeBps);
    i += 8;
    treasuryVaultsAuthority.write(_data, i);
    i += 32;
    putInt64LE(_data, i, treasuryVaultsAuthorityBump);
    i += 8;
    pendingGlobalAdmin.write(_data, i);
    i += 32;
    i += Borsh.write128Array(padding1, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
