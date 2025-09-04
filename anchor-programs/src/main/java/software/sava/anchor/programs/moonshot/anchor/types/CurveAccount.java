package software.sava.anchor.programs.moonshot.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record CurveAccount(PublicKey _address,
                           Discriminator discriminator,
                           long totalSupply,
                           long curveAmount,
                           PublicKey mint,
                           int decimals,
                           Currency collateralCurrency,
                           CurveType curveType,
                           long marketcapThreshold,
                           Currency marketcapCurrency,
                           long migrationFee,
                           int coefB,
                           int bump,
                           MigrationTarget migrationTarget) implements Borsh {

  public static final int BYTES = 82;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int TOTAL_SUPPLY_OFFSET = 8;
  public static final int CURVE_AMOUNT_OFFSET = 16;
  public static final int MINT_OFFSET = 24;
  public static final int DECIMALS_OFFSET = 56;
  public static final int COLLATERAL_CURRENCY_OFFSET = 57;
  public static final int CURVE_TYPE_OFFSET = 58;
  public static final int MARKETCAP_THRESHOLD_OFFSET = 59;
  public static final int MARKETCAP_CURRENCY_OFFSET = 67;
  public static final int MIGRATION_FEE_OFFSET = 68;
  public static final int COEF_B_OFFSET = 76;
  public static final int BUMP_OFFSET = 80;
  public static final int MIGRATION_TARGET_OFFSET = 81;

  public static Filter createTotalSupplyFilter(final long totalSupply) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, totalSupply);
    return Filter.createMemCompFilter(TOTAL_SUPPLY_OFFSET, _data);
  }

  public static Filter createCurveAmountFilter(final long curveAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, curveAmount);
    return Filter.createMemCompFilter(CURVE_AMOUNT_OFFSET, _data);
  }

  public static Filter createMintFilter(final PublicKey mint) {
    return Filter.createMemCompFilter(MINT_OFFSET, mint);
  }

  public static Filter createDecimalsFilter(final int decimals) {
    return Filter.createMemCompFilter(DECIMALS_OFFSET, new byte[]{(byte) decimals});
  }

  public static Filter createCollateralCurrencyFilter(final Currency collateralCurrency) {
    return Filter.createMemCompFilter(COLLATERAL_CURRENCY_OFFSET, collateralCurrency.write());
  }

  public static Filter createCurveTypeFilter(final CurveType curveType) {
    return Filter.createMemCompFilter(CURVE_TYPE_OFFSET, curveType.write());
  }

  public static Filter createMarketcapThresholdFilter(final long marketcapThreshold) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, marketcapThreshold);
    return Filter.createMemCompFilter(MARKETCAP_THRESHOLD_OFFSET, _data);
  }

  public static Filter createMarketcapCurrencyFilter(final Currency marketcapCurrency) {
    return Filter.createMemCompFilter(MARKETCAP_CURRENCY_OFFSET, marketcapCurrency.write());
  }

  public static Filter createMigrationFeeFilter(final long migrationFee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, migrationFee);
    return Filter.createMemCompFilter(MIGRATION_FEE_OFFSET, _data);
  }

  public static Filter createCoefBFilter(final int coefB) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, coefB);
    return Filter.createMemCompFilter(COEF_B_OFFSET, _data);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createMigrationTargetFilter(final MigrationTarget migrationTarget) {
    return Filter.createMemCompFilter(MIGRATION_TARGET_OFFSET, migrationTarget.write());
  }

  public static CurveAccount read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static CurveAccount read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static CurveAccount read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], CurveAccount> FACTORY = CurveAccount::read;

  public static CurveAccount read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var totalSupply = getInt64LE(_data, i);
    i += 8;
    final var curveAmount = getInt64LE(_data, i);
    i += 8;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var decimals = _data[i] & 0xFF;
    ++i;
    final var collateralCurrency = Currency.read(_data, i);
    i += Borsh.len(collateralCurrency);
    final var curveType = CurveType.read(_data, i);
    i += Borsh.len(curveType);
    final var marketcapThreshold = getInt64LE(_data, i);
    i += 8;
    final var marketcapCurrency = Currency.read(_data, i);
    i += Borsh.len(marketcapCurrency);
    final var migrationFee = getInt64LE(_data, i);
    i += 8;
    final var coefB = getInt32LE(_data, i);
    i += 4;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var migrationTarget = MigrationTarget.read(_data, i);
    return new CurveAccount(_address,
                            discriminator,
                            totalSupply,
                            curveAmount,
                            mint,
                            decimals,
                            collateralCurrency,
                            curveType,
                            marketcapThreshold,
                            marketcapCurrency,
                            migrationFee,
                            coefB,
                            bump,
                            migrationTarget);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt64LE(_data, i, totalSupply);
    i += 8;
    putInt64LE(_data, i, curveAmount);
    i += 8;
    mint.write(_data, i);
    i += 32;
    _data[i] = (byte) decimals;
    ++i;
    i += Borsh.write(collateralCurrency, _data, i);
    i += Borsh.write(curveType, _data, i);
    putInt64LE(_data, i, marketcapThreshold);
    i += 8;
    i += Borsh.write(marketcapCurrency, _data, i);
    putInt64LE(_data, i, migrationFee);
    i += 8;
    putInt32LE(_data, i, coefB);
    i += 4;
    _data[i] = (byte) bump;
    ++i;
    i += Borsh.write(migrationTarget, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
