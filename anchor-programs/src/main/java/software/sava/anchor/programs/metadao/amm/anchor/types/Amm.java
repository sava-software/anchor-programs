package software.sava.anchor.programs.metadao.amm.anchor.types;

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

public record Amm(PublicKey _address,
                  Discriminator discriminator,
                  int bump,
                  long createdAtSlot,
                  PublicKey lpMint,
                  PublicKey baseMint,
                  PublicKey quoteMint,
                  int baseMintDecimals,
                  int quoteMintDecimals,
                  long baseAmount,
                  long quoteAmount,
                  TwapOracle oracle,
                  long seqNum,
                  PublicKey vaultAtaBase,
                  PublicKey vaultAtaQuote) implements Borsh {

  public static final int BYTES = 299;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int BUMP_OFFSET = 8;
  public static final int CREATED_AT_SLOT_OFFSET = 9;
  public static final int LP_MINT_OFFSET = 17;
  public static final int BASE_MINT_OFFSET = 49;
  public static final int QUOTE_MINT_OFFSET = 81;
  public static final int BASE_MINT_DECIMALS_OFFSET = 113;
  public static final int QUOTE_MINT_DECIMALS_OFFSET = 114;
  public static final int BASE_AMOUNT_OFFSET = 115;
  public static final int QUOTE_AMOUNT_OFFSET = 123;
  public static final int ORACLE_OFFSET = 131;
  public static final int SEQ_NUM_OFFSET = 227;
  public static final int VAULT_ATA_BASE_OFFSET = 235;
  public static final int VAULT_ATA_QUOTE_OFFSET = 267;

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createCreatedAtSlotFilter(final long createdAtSlot) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, createdAtSlot);
    return Filter.createMemCompFilter(CREATED_AT_SLOT_OFFSET, _data);
  }

  public static Filter createLpMintFilter(final PublicKey lpMint) {
    return Filter.createMemCompFilter(LP_MINT_OFFSET, lpMint);
  }

  public static Filter createBaseMintFilter(final PublicKey baseMint) {
    return Filter.createMemCompFilter(BASE_MINT_OFFSET, baseMint);
  }

  public static Filter createQuoteMintFilter(final PublicKey quoteMint) {
    return Filter.createMemCompFilter(QUOTE_MINT_OFFSET, quoteMint);
  }

  public static Filter createBaseMintDecimalsFilter(final int baseMintDecimals) {
    return Filter.createMemCompFilter(BASE_MINT_DECIMALS_OFFSET, new byte[]{(byte) baseMintDecimals});
  }

  public static Filter createQuoteMintDecimalsFilter(final int quoteMintDecimals) {
    return Filter.createMemCompFilter(QUOTE_MINT_DECIMALS_OFFSET, new byte[]{(byte) quoteMintDecimals});
  }

  public static Filter createBaseAmountFilter(final long baseAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, baseAmount);
    return Filter.createMemCompFilter(BASE_AMOUNT_OFFSET, _data);
  }

  public static Filter createQuoteAmountFilter(final long quoteAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, quoteAmount);
    return Filter.createMemCompFilter(QUOTE_AMOUNT_OFFSET, _data);
  }

  public static Filter createOracleFilter(final TwapOracle oracle) {
    return Filter.createMemCompFilter(ORACLE_OFFSET, oracle.write());
  }

  public static Filter createSeqNumFilter(final long seqNum) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, seqNum);
    return Filter.createMemCompFilter(SEQ_NUM_OFFSET, _data);
  }

  public static Filter createVaultAtaBaseFilter(final PublicKey vaultAtaBase) {
    return Filter.createMemCompFilter(VAULT_ATA_BASE_OFFSET, vaultAtaBase);
  }

  public static Filter createVaultAtaQuoteFilter(final PublicKey vaultAtaQuote) {
    return Filter.createMemCompFilter(VAULT_ATA_QUOTE_OFFSET, vaultAtaQuote);
  }

  public static Amm read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Amm read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Amm read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Amm> FACTORY = Amm::read;

  public static Amm read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var bump = _data[i] & 0xFF;
    ++i;
    final var createdAtSlot = getInt64LE(_data, i);
    i += 8;
    final var lpMint = readPubKey(_data, i);
    i += 32;
    final var baseMint = readPubKey(_data, i);
    i += 32;
    final var quoteMint = readPubKey(_data, i);
    i += 32;
    final var baseMintDecimals = _data[i] & 0xFF;
    ++i;
    final var quoteMintDecimals = _data[i] & 0xFF;
    ++i;
    final var baseAmount = getInt64LE(_data, i);
    i += 8;
    final var quoteAmount = getInt64LE(_data, i);
    i += 8;
    final var oracle = TwapOracle.read(_data, i);
    i += Borsh.len(oracle);
    final var seqNum = getInt64LE(_data, i);
    i += 8;
    final var vaultAtaBase = readPubKey(_data, i);
    i += 32;
    final var vaultAtaQuote = readPubKey(_data, i);
    return new Amm(_address,
                   discriminator,
                   bump,
                   createdAtSlot,
                   lpMint,
                   baseMint,
                   quoteMint,
                   baseMintDecimals,
                   quoteMintDecimals,
                   baseAmount,
                   quoteAmount,
                   oracle,
                   seqNum,
                   vaultAtaBase,
                   vaultAtaQuote);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    _data[i] = (byte) bump;
    ++i;
    putInt64LE(_data, i, createdAtSlot);
    i += 8;
    lpMint.write(_data, i);
    i += 32;
    baseMint.write(_data, i);
    i += 32;
    quoteMint.write(_data, i);
    i += 32;
    _data[i] = (byte) baseMintDecimals;
    ++i;
    _data[i] = (byte) quoteMintDecimals;
    ++i;
    putInt64LE(_data, i, baseAmount);
    i += 8;
    putInt64LE(_data, i, quoteAmount);
    i += 8;
    i += Borsh.write(oracle, _data, i);
    putInt64LE(_data, i, seqNum);
    i += 8;
    vaultAtaBase.write(_data, i);
    i += 32;
    vaultAtaQuote.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
