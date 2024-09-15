package software.sava.anchor.programs.jupiter.limit.anchor.types;

import java.util.OptionalLong;
import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Order(PublicKey _address,
                    Discriminator discriminator,
                    PublicKey maker,
                    PublicKey inputMint,
                    PublicKey outputMint,
                    boolean waiting,
                    long oriMakingAmount,
                    long oriTakingAmount,
                    long makingAmount,
                    long takingAmount,
                    PublicKey makerInputAccount,
                    PublicKey makerOutputAccount,
                    PublicKey reserve,
                    long borrowMakingAmount,
                    OptionalLong expiredAt,
                    PublicKey base,
                    PublicKey referral) implements Borsh {

  public static final int MAKER_OFFSET = 8;
  public static final int INPUT_MINT_OFFSET = 40;
  public static final int OUTPUT_MINT_OFFSET = 72;
  public static final int WAITING_OFFSET = 104;
  public static final int ORI_MAKING_AMOUNT_OFFSET = 105;
  public static final int ORI_TAKING_AMOUNT_OFFSET = 113;
  public static final int MAKING_AMOUNT_OFFSET = 121;
  public static final int TAKING_AMOUNT_OFFSET = 129;
  public static final int MAKER_INPUT_ACCOUNT_OFFSET = 137;
  public static final int MAKER_OUTPUT_ACCOUNT_OFFSET = 169;
  public static final int RESERVE_OFFSET = 201;
  public static final int BORROW_MAKING_AMOUNT_OFFSET = 233;
  public static final int EXPIRED_AT_OFFSET = 241;

  public static Filter createMakerFilter(final PublicKey maker) {
    return Filter.createMemCompFilter(MAKER_OFFSET, maker);
  }

  public static Filter createInputMintFilter(final PublicKey inputMint) {
    return Filter.createMemCompFilter(INPUT_MINT_OFFSET, inputMint);
  }

  public static Filter createOutputMintFilter(final PublicKey outputMint) {
    return Filter.createMemCompFilter(OUTPUT_MINT_OFFSET, outputMint);
  }

  public static Filter createWaitingFilter(final boolean waiting) {
    return Filter.createMemCompFilter(WAITING_OFFSET, new byte[]{(byte) (waiting ? 1 : 0)});
  }

  public static Filter createOriMakingAmountFilter(final long oriMakingAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, oriMakingAmount);
    return Filter.createMemCompFilter(ORI_MAKING_AMOUNT_OFFSET, _data);
  }

  public static Filter createOriTakingAmountFilter(final long oriTakingAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, oriTakingAmount);
    return Filter.createMemCompFilter(ORI_TAKING_AMOUNT_OFFSET, _data);
  }

  public static Filter createMakingAmountFilter(final long makingAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, makingAmount);
    return Filter.createMemCompFilter(MAKING_AMOUNT_OFFSET, _data);
  }

  public static Filter createTakingAmountFilter(final long takingAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, takingAmount);
    return Filter.createMemCompFilter(TAKING_AMOUNT_OFFSET, _data);
  }

  public static Filter createMakerInputAccountFilter(final PublicKey makerInputAccount) {
    return Filter.createMemCompFilter(MAKER_INPUT_ACCOUNT_OFFSET, makerInputAccount);
  }

  public static Filter createMakerOutputAccountFilter(final PublicKey makerOutputAccount) {
    return Filter.createMemCompFilter(MAKER_OUTPUT_ACCOUNT_OFFSET, makerOutputAccount);
  }

  public static Filter createReserveFilter(final PublicKey reserve) {
    return Filter.createMemCompFilter(RESERVE_OFFSET, reserve);
  }

  public static Filter createBorrowMakingAmountFilter(final long borrowMakingAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, borrowMakingAmount);
    return Filter.createMemCompFilter(BORROW_MAKING_AMOUNT_OFFSET, _data);
  }

  public static Filter createExpiredAtFilter(final long expiredAt) {
    final byte[] _data = new byte[9];
    _data[0] = 1;
    putInt64LE(_data, 1, expiredAt);
    return Filter.createMemCompFilter(EXPIRED_AT_OFFSET, _data);
  }

  public static Order read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Order read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Order> FACTORY = Order::read;

  public static Order read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var maker = readPubKey(_data, i);
    i += 32;
    final var inputMint = readPubKey(_data, i);
    i += 32;
    final var outputMint = readPubKey(_data, i);
    i += 32;
    final var waiting = _data[i] == 1;
    ++i;
    final var oriMakingAmount = getInt64LE(_data, i);
    i += 8;
    final var oriTakingAmount = getInt64LE(_data, i);
    i += 8;
    final var makingAmount = getInt64LE(_data, i);
    i += 8;
    final var takingAmount = getInt64LE(_data, i);
    i += 8;
    final var makerInputAccount = readPubKey(_data, i);
    i += 32;
    final var makerOutputAccount = readPubKey(_data, i);
    i += 32;
    final var reserve = readPubKey(_data, i);
    i += 32;
    final var borrowMakingAmount = getInt64LE(_data, i);
    i += 8;
    final var expiredAt = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (expiredAt.isPresent()) {
      i += 8;
    }
    final var base = readPubKey(_data, i);
    i += 32;
    final var referral = _data[i++] == 0 ? null : readPubKey(_data, i);
    return new Order(_address,
                     discriminator,
                     maker,
                     inputMint,
                     outputMint,
                     waiting,
                     oriMakingAmount,
                     oriTakingAmount,
                     makingAmount,
                     takingAmount,
                     makerInputAccount,
                     makerOutputAccount,
                     reserve,
                     borrowMakingAmount,
                     expiredAt,
                     base,
                     referral);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    maker.write(_data, i);
    i += 32;
    inputMint.write(_data, i);
    i += 32;
    outputMint.write(_data, i);
    i += 32;
    _data[i] = (byte) (waiting ? 1 : 0);
    ++i;
    putInt64LE(_data, i, oriMakingAmount);
    i += 8;
    putInt64LE(_data, i, oriTakingAmount);
    i += 8;
    putInt64LE(_data, i, makingAmount);
    i += 8;
    putInt64LE(_data, i, takingAmount);
    i += 8;
    makerInputAccount.write(_data, i);
    i += 32;
    makerOutputAccount.write(_data, i);
    i += 32;
    reserve.write(_data, i);
    i += 32;
    putInt64LE(_data, i, borrowMakingAmount);
    i += 8;
    i += Borsh.writeOptional(expiredAt, _data, i);
    base.write(_data, i);
    i += 32;
    i += Borsh.writeOptional(referral, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 32
         + 32
         + 32
         + 1
         + 8
         + 8
         + 8
         + 8
         + 32
         + 32
         + 32
         + 8
         + (expiredAt == null || expiredAt.isEmpty() ? 1 : 9)
         + 32
         + Borsh.lenOptional(referral, 32);
  }
}
