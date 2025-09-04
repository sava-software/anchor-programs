package software.sava.anchor.programs.jupiter.dca.anchor.types;

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

public record Dca(PublicKey _address,
                  Discriminator discriminator,
                  PublicKey user,
                  PublicKey inputMint,
                  PublicKey outputMint,
                  long idx,
                  long nextCycleAt,
                  long inDeposited,
                  long inWithdrawn,
                  long outWithdrawn,
                  long inUsed,
                  long outReceived,
                  long inAmountPerCycle,
                  long cycleFrequency,
                  long nextCycleAmountLeft,
                  PublicKey inAccount,
                  PublicKey outAccount,
                  long minOutAmount,
                  long maxOutAmount,
                  long keeperInBalanceBeforeBorrow,
                  long dcaOutBalanceBeforeSwap,
                  long createdAt,
                  int bump) implements Borsh {

  public static final int BYTES = 289;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int USER_OFFSET = 8;
  public static final int INPUT_MINT_OFFSET = 40;
  public static final int OUTPUT_MINT_OFFSET = 72;
  public static final int IDX_OFFSET = 104;
  public static final int NEXT_CYCLE_AT_OFFSET = 112;
  public static final int IN_DEPOSITED_OFFSET = 120;
  public static final int IN_WITHDRAWN_OFFSET = 128;
  public static final int OUT_WITHDRAWN_OFFSET = 136;
  public static final int IN_USED_OFFSET = 144;
  public static final int OUT_RECEIVED_OFFSET = 152;
  public static final int IN_AMOUNT_PER_CYCLE_OFFSET = 160;
  public static final int CYCLE_FREQUENCY_OFFSET = 168;
  public static final int NEXT_CYCLE_AMOUNT_LEFT_OFFSET = 176;
  public static final int IN_ACCOUNT_OFFSET = 184;
  public static final int OUT_ACCOUNT_OFFSET = 216;
  public static final int MIN_OUT_AMOUNT_OFFSET = 248;
  public static final int MAX_OUT_AMOUNT_OFFSET = 256;
  public static final int KEEPER_IN_BALANCE_BEFORE_BORROW_OFFSET = 264;
  public static final int DCA_OUT_BALANCE_BEFORE_SWAP_OFFSET = 272;
  public static final int CREATED_AT_OFFSET = 280;
  public static final int BUMP_OFFSET = 288;

  public static Filter createUserFilter(final PublicKey user) {
    return Filter.createMemCompFilter(USER_OFFSET, user);
  }

  public static Filter createInputMintFilter(final PublicKey inputMint) {
    return Filter.createMemCompFilter(INPUT_MINT_OFFSET, inputMint);
  }

  public static Filter createOutputMintFilter(final PublicKey outputMint) {
    return Filter.createMemCompFilter(OUTPUT_MINT_OFFSET, outputMint);
  }

  public static Filter createIdxFilter(final long idx) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, idx);
    return Filter.createMemCompFilter(IDX_OFFSET, _data);
  }

  public static Filter createNextCycleAtFilter(final long nextCycleAt) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, nextCycleAt);
    return Filter.createMemCompFilter(NEXT_CYCLE_AT_OFFSET, _data);
  }

  public static Filter createInDepositedFilter(final long inDeposited) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, inDeposited);
    return Filter.createMemCompFilter(IN_DEPOSITED_OFFSET, _data);
  }

  public static Filter createInWithdrawnFilter(final long inWithdrawn) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, inWithdrawn);
    return Filter.createMemCompFilter(IN_WITHDRAWN_OFFSET, _data);
  }

  public static Filter createOutWithdrawnFilter(final long outWithdrawn) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, outWithdrawn);
    return Filter.createMemCompFilter(OUT_WITHDRAWN_OFFSET, _data);
  }

  public static Filter createInUsedFilter(final long inUsed) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, inUsed);
    return Filter.createMemCompFilter(IN_USED_OFFSET, _data);
  }

  public static Filter createOutReceivedFilter(final long outReceived) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, outReceived);
    return Filter.createMemCompFilter(OUT_RECEIVED_OFFSET, _data);
  }

  public static Filter createInAmountPerCycleFilter(final long inAmountPerCycle) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, inAmountPerCycle);
    return Filter.createMemCompFilter(IN_AMOUNT_PER_CYCLE_OFFSET, _data);
  }

  public static Filter createCycleFrequencyFilter(final long cycleFrequency) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, cycleFrequency);
    return Filter.createMemCompFilter(CYCLE_FREQUENCY_OFFSET, _data);
  }

  public static Filter createNextCycleAmountLeftFilter(final long nextCycleAmountLeft) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, nextCycleAmountLeft);
    return Filter.createMemCompFilter(NEXT_CYCLE_AMOUNT_LEFT_OFFSET, _data);
  }

  public static Filter createInAccountFilter(final PublicKey inAccount) {
    return Filter.createMemCompFilter(IN_ACCOUNT_OFFSET, inAccount);
  }

  public static Filter createOutAccountFilter(final PublicKey outAccount) {
    return Filter.createMemCompFilter(OUT_ACCOUNT_OFFSET, outAccount);
  }

  public static Filter createMinOutAmountFilter(final long minOutAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, minOutAmount);
    return Filter.createMemCompFilter(MIN_OUT_AMOUNT_OFFSET, _data);
  }

  public static Filter createMaxOutAmountFilter(final long maxOutAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, maxOutAmount);
    return Filter.createMemCompFilter(MAX_OUT_AMOUNT_OFFSET, _data);
  }

  public static Filter createKeeperInBalanceBeforeBorrowFilter(final long keeperInBalanceBeforeBorrow) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, keeperInBalanceBeforeBorrow);
    return Filter.createMemCompFilter(KEEPER_IN_BALANCE_BEFORE_BORROW_OFFSET, _data);
  }

  public static Filter createDcaOutBalanceBeforeSwapFilter(final long dcaOutBalanceBeforeSwap) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, dcaOutBalanceBeforeSwap);
    return Filter.createMemCompFilter(DCA_OUT_BALANCE_BEFORE_SWAP_OFFSET, _data);
  }

  public static Filter createCreatedAtFilter(final long createdAt) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, createdAt);
    return Filter.createMemCompFilter(CREATED_AT_OFFSET, _data);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Dca read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Dca read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Dca read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Dca> FACTORY = Dca::read;

  public static Dca read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var user = readPubKey(_data, i);
    i += 32;
    final var inputMint = readPubKey(_data, i);
    i += 32;
    final var outputMint = readPubKey(_data, i);
    i += 32;
    final var idx = getInt64LE(_data, i);
    i += 8;
    final var nextCycleAt = getInt64LE(_data, i);
    i += 8;
    final var inDeposited = getInt64LE(_data, i);
    i += 8;
    final var inWithdrawn = getInt64LE(_data, i);
    i += 8;
    final var outWithdrawn = getInt64LE(_data, i);
    i += 8;
    final var inUsed = getInt64LE(_data, i);
    i += 8;
    final var outReceived = getInt64LE(_data, i);
    i += 8;
    final var inAmountPerCycle = getInt64LE(_data, i);
    i += 8;
    final var cycleFrequency = getInt64LE(_data, i);
    i += 8;
    final var nextCycleAmountLeft = getInt64LE(_data, i);
    i += 8;
    final var inAccount = readPubKey(_data, i);
    i += 32;
    final var outAccount = readPubKey(_data, i);
    i += 32;
    final var minOutAmount = getInt64LE(_data, i);
    i += 8;
    final var maxOutAmount = getInt64LE(_data, i);
    i += 8;
    final var keeperInBalanceBeforeBorrow = getInt64LE(_data, i);
    i += 8;
    final var dcaOutBalanceBeforeSwap = getInt64LE(_data, i);
    i += 8;
    final var createdAt = getInt64LE(_data, i);
    i += 8;
    final var bump = _data[i] & 0xFF;
    return new Dca(_address,
                   discriminator,
                   user,
                   inputMint,
                   outputMint,
                   idx,
                   nextCycleAt,
                   inDeposited,
                   inWithdrawn,
                   outWithdrawn,
                   inUsed,
                   outReceived,
                   inAmountPerCycle,
                   cycleFrequency,
                   nextCycleAmountLeft,
                   inAccount,
                   outAccount,
                   minOutAmount,
                   maxOutAmount,
                   keeperInBalanceBeforeBorrow,
                   dcaOutBalanceBeforeSwap,
                   createdAt,
                   bump);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    user.write(_data, i);
    i += 32;
    inputMint.write(_data, i);
    i += 32;
    outputMint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, idx);
    i += 8;
    putInt64LE(_data, i, nextCycleAt);
    i += 8;
    putInt64LE(_data, i, inDeposited);
    i += 8;
    putInt64LE(_data, i, inWithdrawn);
    i += 8;
    putInt64LE(_data, i, outWithdrawn);
    i += 8;
    putInt64LE(_data, i, inUsed);
    i += 8;
    putInt64LE(_data, i, outReceived);
    i += 8;
    putInt64LE(_data, i, inAmountPerCycle);
    i += 8;
    putInt64LE(_data, i, cycleFrequency);
    i += 8;
    putInt64LE(_data, i, nextCycleAmountLeft);
    i += 8;
    inAccount.write(_data, i);
    i += 32;
    outAccount.write(_data, i);
    i += 32;
    putInt64LE(_data, i, minOutAmount);
    i += 8;
    putInt64LE(_data, i, maxOutAmount);
    i += 8;
    putInt64LE(_data, i, keeperInBalanceBeforeBorrow);
    i += 8;
    putInt64LE(_data, i, dcaOutBalanceBeforeSwap);
    i += 8;
    putInt64LE(_data, i, createdAt);
    i += 8;
    _data[i] = (byte) bump;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
