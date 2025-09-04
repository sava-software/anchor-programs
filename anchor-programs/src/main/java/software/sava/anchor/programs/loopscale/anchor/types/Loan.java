package software.sava.anchor.programs.loopscale.anchor.types;

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
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record Loan(PublicKey _address,
                   Discriminator discriminator,
                   int version,
                   int bump,
                   int loanStatus,
                   PublicKey borrower,
                   long nonce,
                   PodU64 startTime,
                   Ledger[] ledgers,
                   CollateralData[] collateral,
                   PodU32CBPS[][] weightMatrix,
                   PodU32CBPS[][] ltvMatrix,
                   PodU32CBPS[][] lqtMatrix) implements Borsh {

  public static final int BYTES = 1634;
  public static final int LEDGERS_LEN = 5;
  public static final int COLLATERAL_LEN = 5;
  public static final int WEIGHT_MATRIX_LEN = 5;
  public static final int LTV_MATRIX_LEN = 5;
  public static final int LQT_MATRIX_LEN = 5;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(20, 195, 70, 117, 165, 227, 182, 1);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int VERSION_OFFSET = 8;
  public static final int BUMP_OFFSET = 9;
  public static final int LOAN_STATUS_OFFSET = 10;
  public static final int BORROWER_OFFSET = 11;
  public static final int NONCE_OFFSET = 43;
  public static final int START_TIME_OFFSET = 51;
  public static final int LEDGERS_OFFSET = 59;
  public static final int COLLATERAL_OFFSET = 969;
  public static final int WEIGHT_MATRIX_OFFSET = 1334;
  public static final int LTV_MATRIX_OFFSET = 1434;
  public static final int LQT_MATRIX_OFFSET = 1534;

  public static Filter createVersionFilter(final int version) {
    return Filter.createMemCompFilter(VERSION_OFFSET, new byte[]{(byte) version});
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createLoanStatusFilter(final int loanStatus) {
    return Filter.createMemCompFilter(LOAN_STATUS_OFFSET, new byte[]{(byte) loanStatus});
  }

  public static Filter createBorrowerFilter(final PublicKey borrower) {
    return Filter.createMemCompFilter(BORROWER_OFFSET, borrower);
  }

  public static Filter createNonceFilter(final long nonce) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, nonce);
    return Filter.createMemCompFilter(NONCE_OFFSET, _data);
  }

  public static Filter createStartTimeFilter(final PodU64 startTime) {
    return Filter.createMemCompFilter(START_TIME_OFFSET, startTime.write());
  }

  public static Loan read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Loan read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Loan read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Loan> FACTORY = Loan::read;

  public static Loan read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var version = _data[i] & 0xFF;
    ++i;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var loanStatus = _data[i] & 0xFF;
    ++i;
    final var borrower = readPubKey(_data, i);
    i += 32;
    final var nonce = getInt64LE(_data, i);
    i += 8;
    final var startTime = PodU64.read(_data, i);
    i += Borsh.len(startTime);
    final var ledgers = new Ledger[5];
    i += Borsh.readArray(ledgers, Ledger::read, _data, i);
    final var collateral = new CollateralData[5];
    i += Borsh.readArray(collateral, CollateralData::read, _data, i);
    final var weightMatrix = new PodU32CBPS[5][5];
    i += Borsh.readArray(weightMatrix, PodU32CBPS::read, _data, i);
    final var ltvMatrix = new PodU32CBPS[5][5];
    i += Borsh.readArray(ltvMatrix, PodU32CBPS::read, _data, i);
    final var lqtMatrix = new PodU32CBPS[5][5];
    Borsh.readArray(lqtMatrix, PodU32CBPS::read, _data, i);
    return new Loan(_address,
                    discriminator,
                    version,
                    bump,
                    loanStatus,
                    borrower,
                    nonce,
                    startTime,
                    ledgers,
                    collateral,
                    weightMatrix,
                    ltvMatrix,
                    lqtMatrix);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    _data[i] = (byte) version;
    ++i;
    _data[i] = (byte) bump;
    ++i;
    _data[i] = (byte) loanStatus;
    ++i;
    borrower.write(_data, i);
    i += 32;
    putInt64LE(_data, i, nonce);
    i += 8;
    i += Borsh.write(startTime, _data, i);
    i += Borsh.writeArray(ledgers, _data, i);
    i += Borsh.writeArray(collateral, _data, i);
    i += Borsh.writeArray(weightMatrix, _data, i);
    i += Borsh.writeArray(ltvMatrix, _data, i);
    i += Borsh.writeArray(lqtMatrix, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
