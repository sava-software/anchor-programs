package software.sava.anchor.programs.flash.perpetuals.anchor.types;

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

public record Market(PublicKey _address,
                     Discriminator discriminator,
                     PublicKey pool,
                     PublicKey targetCustody,
                     PublicKey collateralCustody,
                     Side side,
                     boolean correlation,
                     long maxPayoffBps,
                     MarketPermissions permissions,
                     long degenExposureUsd,
                     PositionStats collectivePosition,
                     int targetCustodyUid,
                     byte[] padding,
                     int collateralCustodyUid,
                     byte[] padding2,
                     int bump) implements Borsh {

  public static final int BYTES = 246;
  public static final int PADDING_LEN = 7;
  public static final int PADDING_2_LEN = 7;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int POOL_OFFSET = 8;
  public static final int TARGET_CUSTODY_OFFSET = 40;
  public static final int COLLATERAL_CUSTODY_OFFSET = 72;
  public static final int SIDE_OFFSET = 104;
  public static final int CORRELATION_OFFSET = 105;
  public static final int MAX_PAYOFF_BPS_OFFSET = 106;
  public static final int PERMISSIONS_OFFSET = 114;
  public static final int DEGEN_EXPOSURE_USD_OFFSET = 118;
  public static final int COLLECTIVE_POSITION_OFFSET = 126;
  public static final int TARGET_CUSTODY_UID_OFFSET = 229;
  public static final int PADDING_OFFSET = 230;
  public static final int COLLATERAL_CUSTODY_UID_OFFSET = 237;
  public static final int PADDING_2_OFFSET = 238;
  public static final int BUMP_OFFSET = 245;

  public static Filter createPoolFilter(final PublicKey pool) {
    return Filter.createMemCompFilter(POOL_OFFSET, pool);
  }

  public static Filter createTargetCustodyFilter(final PublicKey targetCustody) {
    return Filter.createMemCompFilter(TARGET_CUSTODY_OFFSET, targetCustody);
  }

  public static Filter createCollateralCustodyFilter(final PublicKey collateralCustody) {
    return Filter.createMemCompFilter(COLLATERAL_CUSTODY_OFFSET, collateralCustody);
  }

  public static Filter createSideFilter(final Side side) {
    return Filter.createMemCompFilter(SIDE_OFFSET, side.write());
  }

  public static Filter createCorrelationFilter(final boolean correlation) {
    return Filter.createMemCompFilter(CORRELATION_OFFSET, new byte[]{(byte) (correlation ? 1 : 0)});
  }

  public static Filter createMaxPayoffBpsFilter(final long maxPayoffBps) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, maxPayoffBps);
    return Filter.createMemCompFilter(MAX_PAYOFF_BPS_OFFSET, _data);
  }

  public static Filter createPermissionsFilter(final MarketPermissions permissions) {
    return Filter.createMemCompFilter(PERMISSIONS_OFFSET, permissions.write());
  }

  public static Filter createDegenExposureUsdFilter(final long degenExposureUsd) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, degenExposureUsd);
    return Filter.createMemCompFilter(DEGEN_EXPOSURE_USD_OFFSET, _data);
  }

  public static Filter createCollectivePositionFilter(final PositionStats collectivePosition) {
    return Filter.createMemCompFilter(COLLECTIVE_POSITION_OFFSET, collectivePosition.write());
  }

  public static Filter createTargetCustodyUidFilter(final int targetCustodyUid) {
    return Filter.createMemCompFilter(TARGET_CUSTODY_UID_OFFSET, new byte[]{(byte) targetCustodyUid});
  }

  public static Filter createCollateralCustodyUidFilter(final int collateralCustodyUid) {
    return Filter.createMemCompFilter(COLLATERAL_CUSTODY_UID_OFFSET, new byte[]{(byte) collateralCustodyUid});
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Market read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Market read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Market read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Market> FACTORY = Market::read;

  public static Market read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var pool = readPubKey(_data, i);
    i += 32;
    final var targetCustody = readPubKey(_data, i);
    i += 32;
    final var collateralCustody = readPubKey(_data, i);
    i += 32;
    final var side = Side.read(_data, i);
    i += Borsh.len(side);
    final var correlation = _data[i] == 1;
    ++i;
    final var maxPayoffBps = getInt64LE(_data, i);
    i += 8;
    final var permissions = MarketPermissions.read(_data, i);
    i += Borsh.len(permissions);
    final var degenExposureUsd = getInt64LE(_data, i);
    i += 8;
    final var collectivePosition = PositionStats.read(_data, i);
    i += Borsh.len(collectivePosition);
    final var targetCustodyUid = _data[i] & 0xFF;
    ++i;
    final var padding = new byte[7];
    i += Borsh.readArray(padding, _data, i);
    final var collateralCustodyUid = _data[i] & 0xFF;
    ++i;
    final var padding2 = new byte[7];
    i += Borsh.readArray(padding2, _data, i);
    final var bump = _data[i] & 0xFF;
    return new Market(_address,
                      discriminator,
                      pool,
                      targetCustody,
                      collateralCustody,
                      side,
                      correlation,
                      maxPayoffBps,
                      permissions,
                      degenExposureUsd,
                      collectivePosition,
                      targetCustodyUid,
                      padding,
                      collateralCustodyUid,
                      padding2,
                      bump);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    pool.write(_data, i);
    i += 32;
    targetCustody.write(_data, i);
    i += 32;
    collateralCustody.write(_data, i);
    i += 32;
    i += Borsh.write(side, _data, i);
    _data[i] = (byte) (correlation ? 1 : 0);
    ++i;
    putInt64LE(_data, i, maxPayoffBps);
    i += 8;
    i += Borsh.write(permissions, _data, i);
    putInt64LE(_data, i, degenExposureUsd);
    i += 8;
    i += Borsh.write(collectivePosition, _data, i);
    _data[i] = (byte) targetCustodyUid;
    ++i;
    i += Borsh.writeArrayChecked(padding, 7, _data, i);
    _data[i] = (byte) collateralCustodyUid;
    ++i;
    i += Borsh.writeArrayChecked(padding2, 7, _data, i);
    _data[i] = (byte) bump;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
