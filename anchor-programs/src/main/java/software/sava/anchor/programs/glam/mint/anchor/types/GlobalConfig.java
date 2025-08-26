package software.sava.anchor.programs.glam.mint.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record GlobalConfig(PublicKey _address,
                           Discriminator discriminator,
                           // The authority that can modify the config
                           PublicKey admin,
                           // The authority that can modify fee structure of individual glam state
                           // and claim protocol fees
                           PublicKey feeAuthority,
                           PublicKey referrer,
                           int baseFeeBps,
                           int flowFeeBps,
                           AssetMeta[] assetMetas) implements Borsh {

  public static final Discriminator DISCRIMINATOR = toDiscriminator(149, 8, 156, 202, 160, 252, 176, 217);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int ADMIN_OFFSET = 8;
  public static final int FEE_AUTHORITY_OFFSET = 40;
  public static final int REFERRER_OFFSET = 72;
  public static final int BASE_FEE_BPS_OFFSET = 104;
  public static final int FLOW_FEE_BPS_OFFSET = 106;
  public static final int ASSET_METAS_OFFSET = 108;

  public static Filter createAdminFilter(final PublicKey admin) {
    return Filter.createMemCompFilter(ADMIN_OFFSET, admin);
  }

  public static Filter createFeeAuthorityFilter(final PublicKey feeAuthority) {
    return Filter.createMemCompFilter(FEE_AUTHORITY_OFFSET, feeAuthority);
  }

  public static Filter createReferrerFilter(final PublicKey referrer) {
    return Filter.createMemCompFilter(REFERRER_OFFSET, referrer);
  }

  public static Filter createBaseFeeBpsFilter(final int baseFeeBps) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, baseFeeBps);
    return Filter.createMemCompFilter(BASE_FEE_BPS_OFFSET, _data);
  }

  public static Filter createFlowFeeBpsFilter(final int flowFeeBps) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, flowFeeBps);
    return Filter.createMemCompFilter(FLOW_FEE_BPS_OFFSET, _data);
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
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var admin = readPubKey(_data, i);
    i += 32;
    final var feeAuthority = readPubKey(_data, i);
    i += 32;
    final var referrer = readPubKey(_data, i);
    i += 32;
    final var baseFeeBps = getInt16LE(_data, i);
    i += 2;
    final var flowFeeBps = getInt16LE(_data, i);
    i += 2;
    final var assetMetas = Borsh.readVector(AssetMeta.class, AssetMeta::read, _data, i);
    return new GlobalConfig(_address,
                            discriminator,
                            admin,
                            feeAuthority,
                            referrer,
                            baseFeeBps,
                            flowFeeBps,
                            assetMetas);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    admin.write(_data, i);
    i += 32;
    feeAuthority.write(_data, i);
    i += 32;
    referrer.write(_data, i);
    i += 32;
    putInt16LE(_data, i, baseFeeBps);
    i += 2;
    putInt16LE(_data, i, flowFeeBps);
    i += 2;
    i += Borsh.writeVector(assetMetas, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 32
         + 32
         + 32
         + 2
         + 2
         + Borsh.lenVector(assetMetas);
  }
}
